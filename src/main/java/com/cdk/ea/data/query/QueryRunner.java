package com.cdk.ea.data.query;

import java.util.stream.IntStream;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.types.Type;

import lombok.ToString;

@ToString
public class QueryRunner {
    
    private final Query query; 
    
    private QueryRunner(Query query) {
	this.query = query;
    }

    public DataCollector run() {
	Type dataType = query.getTypeBuilder().buildType();
	Generator<?> generator = dataType.generator();
	DataCollector dataCollector = query.getDataCollector();
	IntStream.rangeClosed(1, query.getQuantity())
			.forEach(i -> dataCollector.getData().add(generator.generate()));
	return dataCollector;
    }
    
    @SuppressWarnings(value = { "all" })
    public static QueryRunner from(String... queryParams) {
	return new QueryRunner(new Query.QueryBuilder().build(queryParams));
    }

}
