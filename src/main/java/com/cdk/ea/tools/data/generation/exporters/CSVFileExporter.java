package com.cdk.ea.tools.data.generation.exporters;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.exception.DataExportException;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.query.json.CsvColumnDetails;
import com.opencsv.CSVWriter;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ToString(of = "filePath")
public class CSVFileExporter implements FileExporter {

    private String filePath;

    // map with csvHeaderName as key and dataRef as value
    private Map<String, String> csvColumnDetails = new LinkedHashMap<>();

    private CSVFileExporter(String filePath, List<CsvColumnDetails> columnDetails) {
	setFilePath(filePath);
	Map<String, String> unsortedCsvColumnDetailsMap = columnDetails.stream()
		.collect(Collectors.toMap(CsvColumnDetails::getHeaderName, CsvColumnDetails::getDataRef));

	// ordering by dataCollector name to ensure correct data is written
	// under correct csv header
	unsortedCsvColumnDetailsMap.entrySet().stream().sorted(Map.Entry.comparingByValue())
		.forEachOrdered(x -> csvColumnDetails.put(x.getKey(), x.getValue()));
    }

    @Override
    public void export(Collection<DataCollector> dataCollectors) {
	try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath), CSVWriter.DEFAULT_SEPARATOR,
		CSVWriter.NO_QUOTE_CHARACTER);) {
	    // write header row for header names as requested by client
	    String[] headers = csvColumnDetails.keySet().toArray(new String[csvColumnDetails.keySet().size()]);
	    csvWriter.writeNext(headers);

	    // get data for relevant data collectors only (which the client has
	    // asked for)
	    // ordering by dataCollector name to ensure correct data is written
	    // under correct csv header
	    Collection<DataCollector> relevantDataCollectors = dataCollectors.stream().sorted()
		    .filter(collector -> csvColumnDetails.values().contains(collector.getName()))
		    .collect(Collectors.toList());

	    String[][] data = getCSVLinesFrom(relevantDataCollectors);
	    Arrays.stream(data).forEach(csvWriter::writeNext); // write data
	    csvWriter.close();
	} catch (Exception e) {
	    throw new DataExportException(e.getMessage());
	}
    }

    // TODO figure out why this method is getting called twice.
    private String[][] getCSVLinesFrom(Collection<DataCollector> dataCollectors) {

	// map whose key is csvHeaderName and value is dataCollector whose data
	// the csvColumn will hold.
	// This map is needed since the relevantDataCollectors passed to this
	// method might be less in
	// case there are different csv columns with same dataCollector.
	// So we need a map which will hold dataCollector as value for each CSV
	// column header (map key)
	Map<String, DataCollector> csvColumnData = new LinkedHashMap<>();
	csvColumnDetails.entrySet().stream().forEachOrdered(
		entry -> csvColumnData.put(entry.getKey(), getDataCollector(entry.getValue(), dataCollectors)));

	// list of list. The inner list is a list of data that each
	// DataCollector holds
	List<List<Object>> dataAsLists = csvColumnData.values().stream().map(collector -> collector.getData())
		.map(collector -> new ArrayList<>(collector)).collect(Collectors.toList());

	List<Integer> dataQuantities = dataCollectors.stream().map(collector -> collector.getData().size())
		.collect(Collectors.toList());
	int totalLines = Collections.max(dataQuantities);
	log.debug("Total {} lines would be written to file {}", totalLines, filePath);
	int totaldataCollectors = csvColumnData.values().size();

	String[][] data = new String[totalLines][totaldataCollectors];

	// TODO Re-factor to simplify
	IntStream.range(0, totalLines).forEach(lineNum -> {
	    IntStream.range(0, totaldataCollectors).forEach(collectorNum -> {
		try {
		    Object colData = dataAsLists.get(collectorNum).get(lineNum);
		    if (null != colData)
			data[lineNum][collectorNum] = colData.toString();
		    else
			data[lineNum][collectorNum] = Constants.EMPTY_STRING;
		} catch (Exception e) {
		    // TODO handle it
		}
	    });
	});

	return data;

    }

    private DataCollector getDataCollector(String collectorName, Collection<DataCollector> dataCollectors) {
	try {
	    return dataCollectors.stream().filter(collector -> collector.getName().equals(collectorName)).findFirst()
		    .get();
	} catch (Exception e) {
	    throw new NoSuchElementException("No collector found for name [" + collectorName + "]. Please check if ["
		    + collectorName + "] is associated with any data generation query");
	}
    }

    @Override
    public void setFilePath(String path) {
	this.filePath = path;
    }

    public static CSVFileExporter from(String filePath, List<CsvColumnDetails> columnDetails) {
	log.debug("Creating instance of CSVFileExporter for csv file {} with data details as {}", filePath,
		columnDetails);
	return new CSVFileExporter(filePath, columnDetails);
    }

}