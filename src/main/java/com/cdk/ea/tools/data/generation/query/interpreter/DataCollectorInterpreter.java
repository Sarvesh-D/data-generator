package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.Identifiers;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;

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
	    String collectorName;
	    if(dataCollectorName.isPresent()) {
		collectorName = dataCollectorName.get();
		log.debug("Data Collector with name [{}] shall be regiesterd",collectorName);
	    } else {
		collectorName = Constants.DEFAULT_DATA_COLLECTOR_NAME;
		log.warn(
			"No Data Collector name specified. Default data collector name [{}] shall be used to register data collector.",
			Constants.DEFAULT_DATA_COLLECTOR_NAME);
	    }
	    queryBuilder.setDataCollector(new DataCollector(collectorName));
	} catch(Exception e) {
	    log.error(
		    "Error occured while interpreting data collector name. Default data collector name [{}] shall be used to register data collector.",
		    Constants.DEFAULT_DATA_COLLECTOR_NAME);
	}
    }

}
