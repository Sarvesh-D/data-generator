package com.cdk.ea.data.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.exception.DataExportException;
import com.cdk.ea.data.exception.QueryInterpretationException;
import com.cdk.ea.data.exporters.CSVFileExporter;
import com.cdk.ea.data.exporters.DataExporter;
import com.cdk.ea.data.query.QueryRunner;
import com.cdk.ea.data.query.interpreter.GlobalDefaultOverrideInterpreter;
import com.cdk.ea.data.query.json.CsvColumnDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataGenerator implements Generator<Collection<DataCollector>> {
    
    private List<QueryRunner> queryRunners = new ArrayList<>();
    
    private List<DataCollector> dataCollectors = new ArrayList<>();
    
    private List<DataExporter> dataExporters = new ArrayList<>(); 

    private DataGenerator(String cmdLineQuery) {
	String completeDataGenQueryString = StringUtils.substringBetween(cmdLineQuery,
		Identifiers.DATA_GEN_QUERY_PREFIX.getIdentifier().toString(), Identifiers.DATA_GEN_QUERY_SUFFIX.getIdentifier().toString());
	
	// gather all CMD queries to generate data
	String[] dataGenQueries = StringUtils.split(completeDataGenQueryString, Identifiers.QUERY_SEPARATOR.getIdentifier());
	log.debug("Data Generation Queries : {}", Arrays.toString(dataGenQueries));
	
	if(org.apache.commons.lang3.ArrayUtils.isEmpty(dataGenQueries))
	    throw new QueryInterpretationException("No Data Generation queries found. Specify data generation queries within (...)");
	
	// build query runner for each data generate query and add to queryRunners
	Arrays.stream(dataGenQueries)
		.filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
		.map(query -> QueryRunner.from(StringUtils.split(query, Constants.SPACE)))
		.forEach(queryRunners::add);
	log.debug("Total Query Runners registered {}.", queryRunners.size());
	
	//  check query for any data exporters
	boolean exportToFile = ArrayUtils.contains(StringUtils.split(cmdLineQuery), Identifiers.FILE.getIdentifier().toString());
	log.info("Export data to CSV set to {}",exportToFile);
	
	if(exportToFile) {
	    String completeDataExportQueryString = StringUtils.substringBetween(cmdLineQuery,
			Identifiers.DATA_EXPORT_QUERY_PREFIX.getIdentifier().toString(), Identifiers.DATA_EXPORT_QUERY_SUFFIX.getIdentifier().toString());
	    
	    // gather all CMD queries to export data
	    String[] dataExportQueries = StringUtils.split(completeDataExportQueryString, Identifiers.QUERY_SEPARATOR.getIdentifier());
	    
	    if(org.apache.commons.lang3.ArrayUtils.isEmpty(dataExportQueries))
		throw new QueryInterpretationException("No Data Export queries found. Specify data generation queries within <...>");
	    
	    log.debug("Data Export Queries : {}", Arrays.toString(dataExportQueries));
	    
	    // build data-exporter for each data export query and add to dataExporters
	    Arrays.stream(dataExportQueries)
	    	.filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
	    	.map(queryStr -> Arrays.asList(StringUtils.split(queryStr)))
	    	.forEach(queryParams -> {
	    	    Optional<String> filePath = queryParams.stream().filter(i -> i.endsWith(".csv")).findFirst();
	    	    if(!filePath.isPresent())
	    		throw new DataExportException("CSV file to export data not specified");
	    	    
	    	    List<String> headerNames = queryParams.stream().filter(i -> i.startsWith(Identifiers.CSV_HEADER_PREFIX.getIdentifier().toString())).map(i -> i.substring(1)).collect(Collectors.toList());
	    	    if(headerNames.isEmpty())
	    		throw new DataExportException("At least one header name must be specified for CSV file export");

	    	    List<String> dataRefs = queryParams.stream().filter(i -> i.startsWith(Identifiers.CSV_COL_DATA_REF.getIdentifier().toString())).map(i -> i.substring(1)).collect(Collectors.toList());
	    	    
	    	    if(headerNames.size() != dataRefs.size()) {
			    throw new DataExportException(String.format(
				    "Total Header Names [%d] and Total Header's Data Refernece [%d] must be of equal number",
				    headerNames.size(), dataRefs.size()));
	    	    }
	    	    
	    	    List<CsvColumnDetails> csvColumnDetails = new ArrayList<>();
	    	    IntStream.range(0, headerNames.size()).forEach(i -> csvColumnDetails.add(new CsvColumnDetails(headerNames.get(i), dataRefs.get(i))));
	    	    
	    	    dataExporters.add(CSVFileExporter.from(filePath.get(), csvColumnDetails));
	    	});
	    log.debug("Total Data Exporters registered {}. Registered data exporters are {}", dataExporters.size(), dataExporters);
	    
	}
    }

    @Override
    public Collection<DataCollector> generate() {
	log.info("beginning to generate data...");
	queryRunners.forEach(queryRunner -> dataCollectors.add(queryRunner.run()));
	log.info("data generation completed...");
	log.debug("Data Collected by {} data collectors.", dataCollectors.size());
	// export data if data exporter is instantiated
	if(!dataExporters.isEmpty())
	    export();
	return dataCollectors;
    }
    
    private void export() {
	log.info("beginning to export data...");
	dataExporters.stream().forEach(dataExporter -> {
	    try {
		dataExporter.export(dataCollectors);
	    } catch(DataExportException e) {
		log.error("Error occured while exporting data : {}",e.getMessage());
	    }
	});
	log.info("data export completed...");
    }

    public static DataGenerator from(String cmdLineQuery) {
	// see if there are global overrides
	if(cmdLineQuery.contains(Constants.GLOBAL_OVERRIDE)) {
	    log.debug("Query contains global override flag {}.", Constants.GLOBAL_OVERRIDE);
	    new GlobalDefaultOverrideInterpreter().doInterpret(null, StringUtils.split(StringUtils.substringAfter(cmdLineQuery, Constants.GLOBAL_OVERRIDE)));
	}
	return new DataGenerator(cmdLineQuery);
    }

}
