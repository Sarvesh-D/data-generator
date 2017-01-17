package com.cdk.ea.data.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.exporters.CSVFileExporter;
import com.cdk.ea.data.exporters.DataExporter;
import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.Generator;

public class DataGenerator implements Generator<Collection<DataCollector>> {
    
    private List<QueryRunner> queryRunners = new ArrayList<>();
    
    private Collection<DataCollector> dataCollectors = new ArrayList<>();
    
    private DataExporter dataExporter; 

    private DataGenerator(String...dataGenParams) {
	String completeQueryString = StringUtils.join(dataGenParams, " ");
	String[] queries = StringUtils.split(completeQueryString, Identifiers.QUERY_SEPARATOR.getIdentifier());
	
	// gather all CMD queries to generate data
	Arrays.stream(queries)
		.map(query -> QueryRunner.from(StringUtils.split(query, " ")))
		.forEach(queryRunners::add);
	
	//  check query for any data exporters
	boolean exportToFile = ArrayUtils.contains(dataGenParams, Identifiers.FILE.getIdentifier().toString());
	if(exportToFile) {
	    // if data exporter is present in query, then instantiate respective data exporter
	    
	    String filePath = Arrays.stream(dataGenParams)
		    		    .filter(i -> i.endsWith(".csv"))
		    		    .findFirst()
		    		    .get();
	    List<String> headers = Arrays.stream(dataGenParams)
                        	    	  .filter(i -> i.charAt(0) == (Identifiers.CSV_HEADER.getIdentifier()))
                        	    	  .map(header -> header.substring(1))
                        	    	  .collect(Collectors.toList());
	    dataExporter = CSVFileExporter.from(filePath, headers.toArray(new String[headers.size()]));
	}
    }

    @Override
    public Collection<DataCollector> generate() {
	queryRunners.forEach(queryRunner -> { 
	    DataCollector dataCollector = new DataCollector();
	    queryRunner.run(dataCollector);
	    dataCollectors.add(dataCollector);
	});
	// export data if data exporter is instantiated
	if(null != dataExporter)
	    export();
	return dataCollectors;
    }
    
    private void export() {
	dataExporter.export(dataCollectors);
    }

    public static DataGenerator from(String... dataGenParams) {
	return new DataGenerator(dataGenParams);
    }

}
