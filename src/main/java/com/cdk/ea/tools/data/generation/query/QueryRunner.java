package com.cdk.ea.tools.data.generation.query;

import java.util.stream.IntStream;

import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.generators.Generator;
import com.cdk.ea.tools.data.generation.types.Type;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryRunner {
    
    private final Query query; 
    
    private QueryRunner(Query query) {
	this.query = query;
    }

    public DataCollector run() {
	Type dataType = query.getTypeBuilder().buildType();
	Generator<?> generator = dataType.generator();
	DataCollector dataCollector = query.getDataCollector();
	log.debug("beginning to run query for type {} with collector name [{}] and data quantity [{}]",dataType, dataCollector.getName(), query.getQuantity());
	IntStream.rangeClosed(1, query.getQuantity())
			.forEach(i -> dataCollector.getData().add(generator.generate()));
	log.debug("query executed successfully");
	return dataCollector;
    }
    
    @SuppressWarnings(value = { "all" })
    public static QueryRunner from(String... queryParams) {
	return new QueryRunner(new Query.QueryBuilder().build(queryParams));
    }

}
