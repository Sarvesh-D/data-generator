package com.ds.tools.data.generator.exporters;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.ds.tools.data.generator.core.Constants;
import com.ds.tools.data.generator.exception.DataExportException;
import com.ds.tools.data.generator.generators.DataCollector;
import com.ds.tools.data.generator.query.json.CsvColumnDetails;
import com.opencsv.CSVWriter;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Out of box implementation for exporting data to a CSV file.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
@ToString(of = "filePath")
public class CSVFileExporter implements FileExporter {

    private String filePath;

    // map with csvHeaderName as key and set of one or more dataRef as value
    private final Map<String, Set<String>> csvColumnDetails = new LinkedHashMap<>();

    /**
     * Constructor for {@link CSVFileExporter}
     *
     * @param filePath
     * @param columnDetails
     * @see #from(String, List)
     */
    private CSVFileExporter(final String filePath, final List<CsvColumnDetails> columnDetails) {
        setFilePath(filePath);
        final Map<String, Set<String>> unsortedCsvColumnDetailsMap = columnDetails.stream()
                                                                                  .collect(Collectors.toMap(CsvColumnDetails::getHeaderName, CsvColumnDetails::getDataRef));

        /*
         * ordering by csv header name for easier look at the generated CSV file.
         */
        unsortedCsvColumnDetailsMap.entrySet()
                                   .stream()
                                   .sorted(Map.Entry.comparingByKey())
                                   .forEachOrdered(x -> csvColumnDetails.put(x.getKey(), x.getValue()));
    }

    /**
     * Factory method to build instance of {@link CSVFileExporter}
     *
     * @param filePath
     *            path of the CSV file to which this exporter shall write the data.
     * @param columnDetails
     *            Collection of {@link CsvColumnDetails}
     * @return new instance of {@link CSVFileExporter} for the desired CSV File.
     */
    public static CSVFileExporter from(final String filePath, final List<CsvColumnDetails> columnDetails) {
        log.debug("Creating instance of CSVFileExporter for csv file {} with data details as {}", filePath, columnDetails);
        return new CSVFileExporter(filePath, columnDetails);
    }

    @Override
    public void export(final Collection<DataCollector> dataCollectors) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);) {
            final long start = System.nanoTime();
            log.debug("Exporting Data to file {} started", filePath);
            // write header row for header names as requested by client
            final String[] headers = csvColumnDetails.keySet()
                                                     .toArray(new String[csvColumnDetails.keySet()
                                                                                         .size()]);
            csvWriter.writeNext(headers);

            /*
             * get data for relevant data collectors only (which the client has asked for).
             * This is to slightly improve performance, since there can be 100
             * data-collectors, but the client may use some of them to export data to a
             * particular CSV file.
             */
            final Collection<DataCollector> relevantDataCollectors = dataCollectors.stream()
                                                                                   .filter(collector -> shouldInclude(collector))
                                                                                   .collect(Collectors.toList());

            final String[][] data = getCSVLinesFrom(relevantDataCollectors);
            Arrays.stream(data)
                  .forEach(csvWriter::writeNext); // write data
            csvWriter.close();
            log.debug("Exporting Data to file {} completed", filePath);
            final long end = System.nanoTime();
            final double timeTaken = (end - start) / 1000000000.0;
            log.debug("Time taken to export data to {} : {} seconds", filePath, timeTaken);
        } catch (final Exception e) {
            throw new DataExportException(e.getMessage());
        }
    }

    @Override
    public void setFilePath(final String path) {
        this.filePath = path;
    }

    /**
     * Extracts and returns data from data collectors. This methods flat maps the
     * result of {@link DataCollector#getData()} for each dataCollector and forms a
     * consolidated list.
     *
     * @param collectors
     *            from which the data is to be extracted
     * @return Flat List of data for each data collector
     */
    private List<Object> extractDataFromCollectors(final Collection<DataCollector> collectors) {
        final List<Object> data = new ArrayList<>();
        collectors.stream()
                  .map(DataCollector::getData)
                  .forEach(data::addAll);
        return data;
    }

    // TODO figure out why this method is getting called twice.
    private String[][] getCSVLinesFrom(final Collection<DataCollector> dataCollectors) {

        /*
         * Map whose key is csvHeaderName and value is dataCollectors whose data the
         * csvColumn will hold. This map is needed since the relevantDataCollectors
         * passed to this method might be less in case there are different csv columns
         * with same dataCollector. So we need a map which will hold dataCollector as
         * value for each CSV column header (map key)
         */
        final Map<String, Set<DataCollector>> csvHeaderToDataMapping = new LinkedHashMap<>();
        csvColumnDetails.entrySet()
                        .stream()
                        .forEachOrdered(entry -> csvHeaderToDataMapping.put(entry.getKey(), getDataCollectors(entry.getValue(), dataCollectors)));

        // list of list. The inner list is a list of data that each
        // DataRef for header holds
        final List<List<Object>> dataAsLists = csvHeaderToDataMapping.values()
                                                                     .stream()
                                                                     .map(collectors -> extractDataFromCollectors(collectors))
                                                                     .collect(Collectors.toList());

        final List<Integer> dataQuantities = dataAsLists.stream()
                                                        .map(dataList -> dataList.size())
                                                        .collect(Collectors.toList());
        final int totalLines = Collections.max(dataQuantities);
        log.debug("Total {} lines would be written to file {}", totalLines, filePath);
        final int totaldataCollectors = csvHeaderToDataMapping.values()
                                                              .size();
        final String[][] data = new String[totalLines][totaldataCollectors];

        // TODO Re-factor to simplify
        /**
         * Builds the data to be written to CSV file. The data is a 2-D array equivalent
         * to CSV row and column space. The data build here is of form [row][coulmn].
         * This 2-D array uniquely identifies what data a particular cell in CSV shall
         * hold.
         */
        IntStream.range(0, totalLines)
                 .forEach(lineNum -> {
                     IntStream.range(0, totaldataCollectors)
                              .forEach(collectorNum -> {
                                  try {
                                      final Object colData = dataAsLists.get(collectorNum)
                                                                        .get(lineNum);
                                      if (null != colData) {
                                          data[lineNum][collectorNum] = colData.toString();
                                      }
                                  } catch (final Exception e) {
                                      /*
                                       * Not all the dataCollectors will hold same amount of data. Hence an exception
                                       * is expected while doing get(lineNum) on a collector which holds less quantity
                                       * of data then the totalLines. In this case we simply write an empty string to
                                       * CSV cell.
                                       */
                                      data[lineNum][collectorNum] = Constants.EMPTY_STRING;
                                  }
                              });
                 });

        return data;

    }

    /**
     * Gets the {@link DataCollector} based on the name provided
     *
     * @param collectorName
     *            name of dataCollector whose object is required
     * @param dataCollectors
     *            collection of dataCollectors from which the required dataCollector
     *            will be fetched
     * @return {@link DataCollector} matching the given name
     * @throws NoSuchElementException
     *             if no dataCollector with given name is found.
     */
    private DataCollector getDataCollector(final String collectorName, final Collection<DataCollector> dataCollectors) {
        try {
            return dataCollectors.stream()
                                 .filter(collector -> collector.getName()
                                                               .equals(collectorName))
                                 .findFirst()
                                 .get();
        } catch (final Exception e) {
            throw new NoSuchElementException("No collector found for name [" + collectorName + "]. Please check if [" + collectorName + "] is associated with any data generation query");
        }
    }

    /**
     * Gets the data collectors for the collector names given
     *
     * @param collectorNames
     *            whose data collectors are required.
     * @param dataCollectors
     *            corresponding to collectorNames
     * @return Set of DataCollectors
     */
    private Set<DataCollector> getDataCollectors(final Set<String> collectorNames, final Collection<DataCollector> dataCollectors) {
        final Set<DataCollector> matchingDataCollectors = new HashSet<>();
        collectorNames.forEach(collectorName -> matchingDataCollectors.add(getDataCollector(collectorName, dataCollectors)));
        return matchingDataCollectors;
    }

    /**
     * Determines whether given data collector is necessary for generating CSV
     * export file
     *
     * @param collector
     *            under question
     * @return boolean True if data collector is required, False otherwise
     */
    private boolean shouldInclude(final DataCollector collector) {
        final Set<String> relevantDataCollectorNames = new HashSet<>();
        csvColumnDetails.values()
                        .stream()
                        .forEach(relevantDataCollectorNames::addAll);
        return relevantDataCollectorNames.contains(collector.getName());
    }

}
