package com.cdk.ea.data.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.Generator;

public class DataGenerator implements Generator<Collection<DataCollector>> {
    
    private List<QueryRunner> queryRunners = new ArrayList<>();
    
    private Collection<DataCollector> dataCollectors = new ArrayList<>();
    
    private DataGenerator(String...dataGenParams) {
	String completeQueryString = StringUtils.join(dataGenParams, " ");
	String[] queries = StringUtils.split(completeQueryString, Identifiers.QUERY_SEPARATOR.getIdentifier());
	Arrays.stream(queries)
		.map(query -> QueryRunner.from(StringUtils.split(query, " ")))
		.forEach(queryRunners::add);
    }

    @Override
    public Collection<DataCollector> generate() {
	queryRunners.forEach(queryRunner -> { 
	    DataCollector dataCollector = new DataCollector();
	    queryRunner.run(dataCollector);
	    dataCollectors.add(dataCollector);
	});
	return dataCollectors;
    }
    
    public static DataGenerator from(String... dataGenParams) {
	return new DataGenerator(dataGenParams);
    }

}
