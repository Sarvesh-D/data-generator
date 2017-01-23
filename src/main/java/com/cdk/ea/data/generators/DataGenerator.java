package com.cdk.ea.data.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.exporters.CSVFileExporter;
import com.cdk.ea.data.exporters.DataExporter;
import com.cdk.ea.data.query.QueryRunner;
import com.cdk.ea.data.query.interpreter.GlobalDefaultOverrideInterpreter;
import com.cdk.ea.data.query.json.CsvColumnDetails;

public class DataGenerator implements Generator<Collection<DataCollector>> {
    
    private List<QueryRunner> queryRunners = new ArrayList<>();
    
    private List<DataCollector> dataCollectors = new ArrayList<>();
    
    private List<DataExporter> dataExporters = new ArrayList<>(); 

    private DataGenerator(String cmdLineQuery) {
	String completeDataGenQueryString = StringUtils.substringBetween(cmdLineQuery,
		Identifiers.DATA_GEN_QUERY_PREFIX.getIdentifier().toString(), Identifiers.DATA_GEN_QUERY_SUFFIX.getIdentifier().toString());
	
	// gather all CMD queries to generate data
	String[] dataGenQueries = StringUtils.split(completeDataGenQueryString, Identifiers.QUERY_SEPARATOR.getIdentifier());
	
	// build query runner for each data generate query and add to queryRunners
	Arrays.stream(dataGenQueries)
		.filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
		.map(query -> QueryRunner.from(StringUtils.split(query, Constants.SPACE)))
		.forEach(queryRunners::add);
	
	//  check query for any data exporters
	boolean exportToFile = ArrayUtils.contains(StringUtils.split(cmdLineQuery), Identifiers.FILE.getIdentifier().toString());
	if(exportToFile) {
	    String completeDataExportQueryString = StringUtils.substringBetween(cmdLineQuery,
			Identifiers.DATA_EXPORT_QUERY_PREFIX.getIdentifier().toString(), Identifiers.DATA_EXPORT_QUERY_SUFFIX.getIdentifier().toString());
	    
	    // gather all CMD queries to export data
	    String[] dataExportQueries = StringUtils.split(completeDataExportQueryString, Identifiers.QUERY_SEPARATOR.getIdentifier());
	    
	    // build data-exporter for each data export query and add to dataExporters
	    Arrays.stream(dataExportQueries)
	    	.filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
	    	.map(queryStr -> Arrays.asList(StringUtils.split(queryStr)))
	    	.forEach(queryParams -> {
	    	    String filePath = queryParams.stream().filter(i -> i.endsWith(".csv")).findFirst().get();
	    	    List<String> headerNames = queryParams.stream().filter(i -> i.startsWith(Identifiers.CSV_HEADER_PREFIX.getIdentifier().toString())).map(i -> i.substring(1)).collect(Collectors.toList());
	    	    List<String> dataRefs = queryParams.stream().filter(i -> i.startsWith(Identifiers.CSV_COL_DATA_REF.getIdentifier().toString())).map(i -> i.substring(1)).collect(Collectors.toList());
	    	    Assert.assertTrue("Header Names and Header's Data Ref must be of equal number", headerNames.size() == dataRefs.size());
	    	    
	    	    List<CsvColumnDetails> csvColumnDetails = new ArrayList<>();
	    	    IntStream.range(0, headerNames.size()).forEach(i -> csvColumnDetails.add(new CsvColumnDetails(headerNames.get(i), dataRefs.get(i))));
	    	    
	    	    dataExporters.add(CSVFileExporter.from(filePath, csvColumnDetails));
	    	});
	    
	}
    }

    @Override
    public Collection<DataCollector> generate() {
	queryRunners.forEach(queryRunner -> dataCollectors.add(queryRunner.run()));
	// export data if data exporter is instantiated
	if(!dataExporters.isEmpty())
	    export();
	return dataCollectors;
    }
    
    private void export() {
	dataExporters.stream().forEach(dataExporter -> dataExporter.export(dataCollectors));
    }

    public static DataGenerator from(String cmdLineQuery) {
	// see if there are global overrides
	if(cmdLineQuery.contains(Constants.GLOBAL_OVERRIDE)) {
	    new GlobalDefaultOverrideInterpreter().doInterpret(null, StringUtils.split(StringUtils.substringAfter(cmdLineQuery, Constants.GLOBAL_OVERRIDE)));
	}
	return new DataGenerator(cmdLineQuery);
    }

}
