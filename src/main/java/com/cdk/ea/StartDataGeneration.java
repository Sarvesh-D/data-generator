package com.cdk.ea;

import java.util.Collection;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.DataGenerator;
import com.cdk.ea.data.query.converter.CmdQueryConverter;
import com.cdk.ea.data.query.converter.JsonQueryConverter;
import com.cdk.ea.data.query.converter.QueryConverter;
import com.cdk.ea.data.query.holder.CmdQueryHolder;
import com.cdk.ea.data.query.holder.JsonQueryHolder;

public class StartDataGeneration {
    
    private StartDataGeneration() {
	// suppressing default constructor
    }

    public static void main(String... args) {
	//QueryRunner.from(args).run();
	//DataGenerator dataGenerator = DataGenerator.from(args);
	Collection<DataCollector> dataCollected = null;
	String query = null;
	
	if("json".equals(args[0])) {
	    JsonQueryConverter jsonQueryConverter = new JsonQueryConverter();
	    jsonQueryConverter.init(args);
	    JsonQueryHolder jsonQueryHolder = new JsonQueryHolder();
	    query = jsonQueryHolder.getQuery(jsonQueryConverter);
	} else {
	    CmdQueryConverter cmdQueryConverter = new CmdQueryConverter();
	    cmdQueryConverter.init(args);
	    CmdQueryHolder cmdQueryHolder = new CmdQueryHolder();
	    query = cmdQueryHolder.getQuery(cmdQueryConverter);
	}
	dataCollected = DataGenerator.from(query).generate();
	dataCollected.stream().forEach(System.out::println);
    }

}
