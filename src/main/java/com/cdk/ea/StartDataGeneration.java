package com.cdk.ea;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.generators.DataGenerator;
import com.cdk.ea.data.query.json.JsonQueryBuilder;

import lombok.extern.java.Log;

@Log
public class StartDataGeneration {
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	String finalCMDQuery;
	
	long start = System.nanoTime();
	
	if("json".equals(args[0])) {
	    String[] jsonFiles = Arrays.stream(args).filter(arg -> arg.endsWith(".json")).toArray(size -> new String[size]);
	    finalCMDQuery = new JsonQueryBuilder().build(jsonFiles);
	}
	else
	    finalCMDQuery = StringUtils.join(args, Constants.SPACE);
	
	DataGenerator.from(finalCMDQuery).generate();

	long end = System.nanoTime();
	double timeTaken = (end - start)/1000000000.0;
	
	log.info("Time Taken to generate Data : "+timeTaken+" secs");
    }

}
