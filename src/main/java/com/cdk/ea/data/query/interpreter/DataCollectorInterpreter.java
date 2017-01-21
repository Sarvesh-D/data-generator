package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.exception.InterpretationException;
import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.query.Query;
import com.cdk.ea.data.query.Query.QueryBuilder;

class DataCollectorInterpreter implements Interpreter {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	try {
	    String dataCollectorName = Arrays.stream(identifiers)
	    		.filter(i -> i.charAt(0) == Identifiers.DATA_COLLECTOR_PREFIX.getIdentifier())
	    		.map(i -> i.substring(1))
	    		.findFirst()
	    		.get();
	    queryBuilder.setDataCollector(new DataCollector(dataCollectorName));
	} catch(Exception e) {
	    throw new InterpretationException("Data Collector name not specified. Specify data collector name using @<collector name>");
	}
    }

}
