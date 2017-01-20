package com.cdk.ea;

import java.util.Collection;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.query.DataGenerator;
import com.cdk.ea.data.query.json.JsonQueryBuilder;

public class StartDataGeneration {
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	//QueryRunner.from(args).run();
	//DataGenerator dataGenerator = DataGenerator.from(args);
	Collection<DataCollector> dataCollected = null;
	
	if("json".equals(args[0])) {
	    dataCollected = DataGenerator.from(new JsonQueryBuilder().build(args[1])).generate();
	} else {
	    dataCollected = DataGenerator.from(args).generate();
	}
	dataCollected.stream().forEach(System.out::println);
    }

}
