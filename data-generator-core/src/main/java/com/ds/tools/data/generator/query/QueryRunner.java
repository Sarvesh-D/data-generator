package com.ds.tools.data.generator.query;

import java.util.stream.IntStream;

import com.ds.tools.data.generator.generators.DataCollector;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.types.Type;

import lombok.extern.slf4j.Slf4j;

/**
 * Runner class which is responsible to run a single {@link Query}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
public class QueryRunner {

    private final Query query;

    private QueryRunner(final Query query) {
        this.query = query;
    }

    /**
     * Factory method to instantiate {@link QueryRunner}
     *
     * @param query
     *            from which the runner must be instantiated.
     * @return {@link QueryRunner}
     */
    @SuppressWarnings(value = { "all" })
    public static QueryRunner from(final String query) {
        return new QueryRunner(new Query.QueryBuilder().build(query));
    }

    /**
     * Runs the encapsulated {@link Query} and collects the data.
     *
     * @return {@link DataCollector} containing the data generated by {@link Query}
     */
    public DataCollector run() {
        final Type dataType = query.getTypeBuilder()
                                   .buildType();
        final Generator<?> generator = dataType.generator();
        final DataCollector dataCollector = query.getDataCollector();
        log.debug("beginning to run query for type [{}] with collector name [{}] and data quantity [{}]", dataType, dataCollector.getName(), query.getQuantity());
        final long start = System.nanoTime();
        IntStream.rangeClosed(1, query.getQuantity())
                 .forEach(i -> dataCollector.getData()
                                            .add(generator.generate()));
        log.debug("query executed successfully");
        final long end = System.nanoTime();
        final double timeTaken = (end - start) / 1000000000.0;
        log.debug("Time taken to execute query for data collector {} : {} seconds", dataCollector.getName(), timeTaken);
        return dataCollector;
    }

}
