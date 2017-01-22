package com.cdk.ea;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.DataGenerator;
import com.cdk.ea.data.query.json.JsonQueryBuilder;

public class StartDataGeneration {
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	String finalCMDQuery;
	Collection<DataCollector> dataCollected;
	
	if("json".equals(args[0])) {
	    String[] jsonFiles = Arrays.stream(args).filter(arg -> arg.endsWith(".json")).toArray(size -> new String[size]);
	    finalCMDQuery = new JsonQueryBuilder().build(jsonFiles);
	}
	else
	    finalCMDQuery = StringUtils.join(args, " ");
	
	dataCollected = DataGenerator.from(finalCMDQuery).generate();
	dataCollected.stream().forEach(System.out::println);
    }

}
