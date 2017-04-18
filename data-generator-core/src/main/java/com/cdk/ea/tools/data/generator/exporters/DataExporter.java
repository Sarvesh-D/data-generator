package com.cdk.ea.tools.data.generator.exporters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generator.common.DataGeneratorUtils;
import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.core.Identifiers;
import com.cdk.ea.tools.data.generator.exception.DataExportException;
import com.cdk.ea.tools.data.generator.generators.DataCollector;
import com.cdk.ea.tools.data.generator.generators.DataGenerator;
import com.cdk.ea.tools.data.generator.query.json.CsvColumnDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * Core Class for exporting data. This class is responsible for executing the
 * data export on the data collected by DataGenerator.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 *
 * @since 16-02-2017
 * @version 1.0
 * @see DataGenerator
 * @see Exporter
 */
@Slf4j
public class DataExporter implements Exporter {

    private List<Exporter> dataExporters = new ArrayList<>();

    private DataExporter(String[] dataExportQueries) {
	log.debug("Data Export Queries : {}", Arrays.toString(dataExportQueries));
	registerDataExporters(dataExportQueries);
    }

    /**
     * Factory method to get instance of {@link DataExporter}
     * 
     * @param dataExportQueries
     *            for {@link DataExporter}
     * @return {@link DataExporter}
     * @see DataGeneratorUtils#getDataExportQueries(String)
     */
    public static DataExporter from(String[] dataExportQueries) {
	return new DataExporter(dataExportQueries);
    }

    /**
     * {@inheritDoc}. This implementation uses {@link CSVFileExporter} to export
     * the data.
     */
    @Override
    public void export(Collection<DataCollector> dataCollectors) {
	long start = System.nanoTime();
	log.info("beginning to export data...");
	dataExporters.stream().forEach(dataExporter -> {
	    try {
		dataExporter.export(dataCollectors);
	    } catch (DataExportException e) {
		log.error("Error occured while exporting data : {}", e.getMessage());
	    }
	});
	log.info("data export completed...");
	final long end = System.nanoTime();
	final double timeTaken = (end - start) / 1000000000.0;
	log.info("Time taken to export data : {} seconds", timeTaken);
    }

    /**
     * Registers one or more data exporters as specified by dataExportQueries
     * 
     * @param dataExportQueries
     *            for registering one or more dataExporters
     */
    private void registerDataExporters(String[] dataExportQueries) {
	// build data-exporter for each data export query and add to
	// dataExporters
	Arrays.stream(dataExportQueries).filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
		.map(queryStr -> Arrays.asList(StringUtils.split(queryStr))).forEach(queryParams -> {
		    Optional<String> filePath = queryParams.stream().filter(i -> i.endsWith(".csv")).findFirst();
		    if (!filePath.isPresent())
			throw new DataExportException("CSV file to export data not specified");

		    List<String> headerNames = queryParams.stream()
			    .filter(i -> i.startsWith(Identifiers.CSV_HEADER_PREFIX.getIdentifier().toString()))
			    .map(i -> i.substring(1)).collect(Collectors.toList());
		    if (headerNames.isEmpty())
			throw new DataExportException("At least one header name must be specified for CSV file export");

		    List<Set<String>> dataRefs = queryParams.stream()
			    .filter(i -> i.startsWith(Identifiers.CSV_COL_DATA_REF.getIdentifier().toString()))
			    .map(i -> org.apache.commons.lang3.StringUtils.split(i.substring(1), Constants.COMMA))
			    .map(dataRefsForHeader -> new HashSet<>(Arrays.asList(dataRefsForHeader)))
			    .collect(Collectors.toList());

		    if (headerNames.size() != dataRefs.size()) {
			throw new DataExportException(String.format(
				"Total Header Names [%d] and Total Header's Data Refernece [%d] must be of equal number",
				headerNames.size(), dataRefs.size()));
		    }

		    List<CsvColumnDetails> csvColumnDetails = new ArrayList<>();
		    IntStream.range(0, headerNames.size()).forEach(
			    i -> csvColumnDetails.add(new CsvColumnDetails(headerNames.get(i), dataRefs.get(i))));

		    dataExporters.add(CSVFileExporter.from(filePath.get(), csvColumnDetails));
		});
	log.debug("Total Data Exporters registered {}. Registered data exporters are {}", dataExporters.size(),
		dataExporters);

    }

}
