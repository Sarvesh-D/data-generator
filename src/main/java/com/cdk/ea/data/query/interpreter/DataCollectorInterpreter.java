package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.exception.QueryInterpretationException;
import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class DataCollectorInterpreter implements Interpreter {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	try {
	    Optional<String> dataCollectorName = Arrays.stream(identifiers)
	    		.filter(i -> i.charAt(0) == Identifiers.DATA_COLLECTOR_PREFIX.getIdentifier())
	    		.map(i -> i.substring(1))
	    		.findFirst();
	    if(dataCollectorName.isPresent()) {
		log.debug("Data Collector with name [{}] shall be registered", dataCollectorName.get());
		queryBuilder.setDataCollector(new DataCollector(dataCollectorName.get()));
	    }
	} catch(Exception e) {
	    throw new QueryInterpretationException("Data Collector name not specified. Specify data collector name using @<collector name>");
	}
    }

}
