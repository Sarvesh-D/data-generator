package com.cdk.ea.data.query;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

import com.cdk.ea.data.types.Type;

public class QueryRunner implements Runnable {
    
    private final Query query; 
    
    private QueryRunner(Query query) {
	this.query = query;
    }

    @Override
    public void run() {
	Set<Object> generatedData = new HashSet<>();
	Type dataType = query.getTypeBuilder().buildType();
	IntStream.rangeClosed(1, query.getQuantity())
			.forEach(i -> generatedData.add(dataType.generator().generate()));
	generatedData.stream().forEach(System.out::println);
    }
    
    @SuppressWarnings(value = { "all" })
    public static QueryRunner from(String... queryParams) {
	return new QueryRunner(new Query.QueryBuilder().build(queryParams));
    }

}
