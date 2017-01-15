package com.cdk.ea.data.query;

import java.util.stream.IntStream;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.types.Type;

import lombok.ToString;

@ToString
class QueryRunner {
    
    private final Query query; 
    
    private QueryRunner(Query query) {
	this.query = query;
    }

    public void run(DataCollector dataCollector) {
	Type dataType = query.getTypeBuilder().buildType();
	Generator<?> generator = dataType.generator();
	IntStream.rangeClosed(1, query.getQuantity())
			.forEach(i -> dataCollector.getData().add(generator.generate()));
    }
    
    @SuppressWarnings(value = { "all" })
    static QueryRunner from(String... queryParams) {
	return new QueryRunner(new Query.QueryBuilder().build(queryParams));
    }

}
