package com.cdk.ea.tools.data.generation.query;

import java.util.stream.IntStream;

import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.generators.Generator;
import com.cdk.ea.tools.data.generation.types.Type;

import lombok.extern.slf4j.Slf4j;

/**
 * Runner class which is responsible to run a single {@link Query}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
public class QueryRunner {

    private final Query query;

    private QueryRunner(Query query) {
	this.query = query;
    }

    /**
     * Factory method to instantiate {@link QueryRunner}
     * 
     * @param queryParams
     *            from which the runner must be instantiated.
     * @return {@link QueryRunner}
     */
    @SuppressWarnings(value = { "all" })
    public static QueryRunner from(String... queryParams) {
	return new QueryRunner(new Query.QueryBuilder().build(queryParams));
    }

    /**
     * Runs the encapsulated {@link Query} and collects the data.
     * 
     * @return {@link DataCollector} containing the data generated by
     *         {@link Query}
     */
    public DataCollector run() {
	Type dataType = query.getTypeBuilder().buildType();
	Generator<?> generator = dataType.generator();
	DataCollector dataCollector = query.getDataCollector();
	log.debug("beginning to run query for type [{}] with collector name [{}] and data quantity [{}]", dataType,
		dataCollector.getName(), query.getQuantity());
	long start = System.nanoTime();
	IntStream.rangeClosed(1, query.getQuantity()).forEach(i -> dataCollector.getData().add(generator.generate()));
	log.debug("query executed successfully");
	final long end = System.nanoTime();
	final double timeTaken = (end - start) / 1000000000.0;
	log.debug("Time taken to execute query for data collector {} : {} seconds", dataCollector.getName(), timeTaken);
	return dataCollector;
    }

}
