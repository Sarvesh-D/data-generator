package com.cdk.ea.tools.data.generation.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.Identifiers;
import com.cdk.ea.tools.data.generation.exception.DataExportException;
import com.cdk.ea.tools.data.generation.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generation.exporters.CSVFileExporter;
import com.cdk.ea.tools.data.generation.exporters.DataExporter;
import com.cdk.ea.tools.data.generation.query.QueryRunner;
import com.cdk.ea.tools.data.generation.query.interpreter.GlobalDefaultOverrideInterpreter;
import com.cdk.ea.tools.data.generation.query.json.CsvColumnDetails;

import lombok.extern.slf4j.Slf4j;

/**
 * Core Class for Data Generator. This class is responsible for executing the
 * data generation and data export queries.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
public class DataGenerator implements Generator<Collection<DataCollector>> {

    private List<QueryRunner> dataQueryRunners = new ArrayList<>();

    private List<DataCollector> dataCollectors = new ArrayList<>();

    private List<DataExporter> dataExporters = new ArrayList<>();

    /**
     * Constructor for registering {@link #dataQueryRunners},
     * {@link #dataCollectors} and {@link #dataExporters}
     * 
     * @param cmdLineQuery
     *            having the details for the queries
     */
    private DataGenerator(String cmdLineQuery) {
	/*
	 * Default Override must happen before any data generation queries are
	 * interpreted and registered with query runners.
	 */
	checkAndOverrideQueryDefaults(cmdLineQuery);

	String[] dataGenQueries = buildDataGenQueries(cmdLineQuery);
	log.debug("Data Generation Queries : {}", Arrays.toString(dataGenQueries));

	registerDataQueryRunners(dataGenQueries);

	if (shouldExportToFile(cmdLineQuery)) {
	    String[] dataExportQueries = buildDataExportQueries(cmdLineQuery);
	    log.debug("Data Export Queries : {}", Arrays.toString(dataExportQueries));
	    registerDataExporters(dataExportQueries);
	}

    }

    /**
     * Factory method to get instance of DataGenerator
     * 
     * @param cmdLineQuery
     * @return {@link DataGenerator}
     */
    public static DataGenerator from(String cmdLineQuery) {
	return new DataGenerator(cmdLineQuery);
    }

    /**
     * {@inheritDoc}. The data collected by this method will be exported if any
     * data exporters have been registered.
     * 
     * @return This method will return Collection of data as collected by
     *         various dataCollectors.
     */
    @Override
    public Collection<DataCollector> generate() {
	log.info("beginning to generate data...");
	dataQueryRunners.forEach(queryRunner -> dataCollectors.add(queryRunner.run()));
	log.info("data generation completed...");
	log.debug("Data Collected by {} data collectors.", dataCollectors.size());
	// export data at-least one data exporter is registered.
	if (!dataExporters.isEmpty())
	    export();
	return dataCollectors;
    }

    /**
     * Extracts one or more dataExportQueries from the complete CLI Query
     * passed.
     * 
     * @param cmdLineQuery
     *            to extract data export queries from
     * @return array containing one or more dataExport queries.
     */
    private String[] buildDataExportQueries(String cmdLineQuery) {
	String completeDataExportQueryString = StringUtils.substringBetween(cmdLineQuery,
		Identifiers.DATA_EXPORT_QUERY_PREFIX.getIdentifier().toString(),
		Identifiers.DATA_EXPORT_QUERY_SUFFIX.getIdentifier().toString());

	// gather all CMD queries to export data
	String[] dataExportQueries = StringUtils.split(completeDataExportQueryString,
		Identifiers.QUERY_SEPARATOR.getIdentifier());

	if (org.apache.commons.lang3.ArrayUtils.isEmpty(dataExportQueries))
	    throw new QueryInterpretationException(
		    "No Data Export queries found. Specify data generation queries within <...>");
	return dataExportQueries;
    }

    /**
     * Extracts one or more dataGenerationQueries from the complete CLI Query
     * passed.
     * 
     * @param cmdLineQuery
     *            to extract data generation queries from
     * @return array containing one or more dataGeneration queries.
     */
    private String[] buildDataGenQueries(String cmdLineQuery) {
	String completeDataGenQueryString = StringUtils.substringBetween(cmdLineQuery,
		Identifiers.DATA_GEN_QUERY_PREFIX.getIdentifier().toString(),
		Identifiers.DATA_GEN_QUERY_SUFFIX.getIdentifier().toString());

	// gather all CMD queries to generate data
	String[] dataGenQueries = StringUtils.split(completeDataGenQueryString,
		Identifiers.QUERY_SEPARATOR.getIdentifier());

	if (org.apache.commons.lang3.ArrayUtils.isEmpty(dataGenQueries))
	    throw new QueryInterpretationException(
		    "No Data Generation queries found. Specify data generation queries within (...)");
	return dataGenQueries;
    }

    /**
     * Checks and invokes {@link GlobalDefaultOverrideInterpreter} if global
     * override flag is present
     * 
     * @param cmdLineQuery
     */
    private void checkAndOverrideQueryDefaults(String cmdLineQuery) {
	log.debug("checking for global overrides flag if present");
	// see if there are global overrides
	if (cmdLineQuery.contains(Constants.GLOBAL_OVERRIDE)) {
	    log.debug("Query contains global override flag {}.", Constants.GLOBAL_OVERRIDE);
	    new GlobalDefaultOverrideInterpreter().doInterpret(null,
		    StringUtils.split(StringUtils.substringAfter(cmdLineQuery, Constants.GLOBAL_OVERRIDE)));
	}
    }

    /**
     * Exports the data using various data exporters registered.
     */
    private void export() {
	log.info("beginning to export data...");
	dataExporters.stream().forEach(dataExporter -> {
	    try {
		dataExporter.export(dataCollectors);
	    } catch (DataExportException e) {
		log.error("Error occured while exporting data : {}", e.getMessage());
	    }
	});
	log.info("data export completed...");
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

		    List<String> dataRefs = queryParams.stream()
			    .filter(i -> i.startsWith(Identifiers.CSV_COL_DATA_REF.getIdentifier().toString()))
			    .map(i -> i.substring(1)).collect(Collectors.toList());

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

    /**
     * Registers the query runners for given dataGeneration queries. These query
     * runners are then invoked to start data generation process.
     * 
     * @param dataGenQueries
     */
    private void registerDataQueryRunners(String[] dataGenQueries) {
	// build query runner for each data generate query and add to
	// dataQueryRunners
	Arrays.stream(dataGenQueries).filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
		.map(query -> QueryRunner.from(StringUtils.split(query, Constants.SPACE)))
		.forEach(dataQueryRunners::add);
	log.debug("Total Query Runners registered {}.", dataQueryRunners.size());
    }

    /**
     * Checks the CLI query for data export flag
     * 
     * @param cmdLineQuery
     * @return <code>True</code> if data export flag is present,
     *         <code>False</code> otherwise.
     */
    private boolean shouldExportToFile(String cmdLineQuery) {
	// check query for any data exporters
	boolean exportToFile = ArrayUtils.contains(StringUtils.split(cmdLineQuery),
		Identifiers.FILE.getIdentifier().toString());
	log.info("Export data to CSV set to {}", exportToFile);
	return exportToFile;
    }

}
