package com.cdk.ea;

import java.util.Collection;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.query.DataGenerator;

public class StartDataGeneration {
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	//QueryRunner.from(args).run();
	//DataGenerator dataGenerator = DataGenerator.from(args);
	Collection<DataCollector> dataCollected = DataGenerator.from(args).generate();
	dataCollected.stream().forEach(System.out::println);
    }

}
