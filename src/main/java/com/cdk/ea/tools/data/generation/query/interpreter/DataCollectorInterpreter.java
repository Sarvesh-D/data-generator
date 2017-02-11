package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import com.cdk.ea.tools.data.generation.core.Defaults;
import com.cdk.ea.tools.data.generation.core.Identifiers;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.query.Query;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Interpreter to interpret the name of the {@link DataCollector} for a single
 * DataGeneration {@link Query}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
class DataCollectorInterpreter implements Interpreter {

    /**
     * {@inheritDoc}. This method interprets the {@link DataCollector} name and
     * populates {@link QueryBuilder#setDataCollector(DataCollector)}. If no
     * name is identified sets the default name as
     * {@link Defaults#DEFAULT_DATA_COLLECTOR_NAME}
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	try {
	    Optional<String> dataCollectorName = Arrays.stream(identifiers)
		    .filter(i -> i.charAt(0) == Identifiers.DATA_COLLECTOR_PREFIX.getIdentifier())
		    .map(i -> i.substring(1)).findFirst();
	    String collectorName;
	    if (dataCollectorName.isPresent()) {
		collectorName = dataCollectorName.get();
		log.debug("Data Collector with name [{}] shall be regiesterd", collectorName);
	    } else {
		collectorName = Defaults.DEFAULT_DATA_COLLECTOR_NAME;
		log.warn(
			"No Data Collector name specified. Default data collector name [{}] shall be used to register data collector.",
			Defaults.DEFAULT_DATA_COLLECTOR_NAME);
	    }
	    queryBuilder.setDataCollector(new DataCollector(collectorName));
	} catch (Exception e) {
	    log.error(
		    "Error occured while interpreting data collector name. Default data collector name [{}] shall be used to register data collector.",
		    Defaults.DEFAULT_DATA_COLLECTOR_NAME);
	}
    }

}
