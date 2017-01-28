package com.cdk.ea;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.generators.DataGenerator;
import com.cdk.ea.data.query.json.JsonQueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartDataGeneration {
    
    private static ConsoleAppender console = new ConsoleAppender();
    
    static {
	String pattern = "%-5p: %c - %m%n";
	console.setLayout(new PatternLayout(pattern));
	console.setThreshold(Level.INFO);
	console.activateOptions();
	Logger.getRootLogger().addAppender(console);
    }
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	if(ArrayUtils.contains(args, Constants.DEBUG_ENABLED))
	    console.setThreshold(Level.DEBUG);
	
	String finalCMDQuery;
	
	long start = System.nanoTime();
	
	if(Constants.JSON.equals(args[0])) {
	    log.debug("JSON format selected to generate data");
	    String[] jsonFiles = Arrays.stream(args).filter(arg -> arg.endsWith(Constants.JSON_EXTENSTION)).toArray(size -> new String[size]);
	    log.debug("JSON files to generate data : {}", Arrays.toString(jsonFiles));
	    finalCMDQuery = new JsonQueryBuilder().build(jsonFiles);
	}
	else
	    finalCMDQuery = StringUtils.join(args, Constants.SPACE);
	
	log.debug("Final query to generate data => {}",finalCMDQuery);
	try {
	    DataGenerator.from(finalCMDQuery).generate();
	} catch(Exception e) {
	    log.error("something went wrong... {}",e.getClass().getName());
	}

	long end = System.nanoTime();
	double timeTaken = (end - start)/1000000000.0;
	
	log.info("Time Taken to generate Data : {} seconds",timeTaken);
    }
    

}
