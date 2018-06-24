package com.ds.tools.data.generator.query;

import com.ds.tools.data.generator.common.Builder;
import com.ds.tools.data.generator.generators.DataCollector;
import com.ds.tools.data.generator.query.interpreter.Interpreter;
import com.ds.tools.data.generator.query.interpreter.Interpreters;
import com.ds.tools.data.generator.types.Type;
import com.ds.tools.data.generator.types.TypeBuilder;
import com.ds.tools.data.generator.types.TypeProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value class for holding a single DataGeneration Query
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see QueryBuilder
 */
@Getter
@ToString
@Slf4j
public final class Query {

    /**
     * Helper Builder class for building {@link Query} object
     *
     * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
     * @since 10-02-2017
     * @version 1.0
     */
    @Setter
    @Slf4j
    public static class QueryBuilder implements Builder<Query, String> {

        private TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;

        private int quantity;

        private DataCollector dataCollector;

        @Override
        public Query build(final String query) {
            log.debug("Building Query Instance from params {}", query);
            final QueryBuilder queryBuilder = new QueryBuilder();
            final Interpreter queryInterpreter = Interpreters.QUERY_INTERPRETER.get();
            queryInterpreter.doInterpret(queryBuilder, query.toString());
            return new Query(queryBuilder);
        }

    }

    private final TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;

    private final int quantity;

    private final DataCollector dataCollector;

    private Query(final QueryBuilder queryBuilder) {
        this.typeBuilder = queryBuilder.typeBuilder;
        this.quantity = queryBuilder.quantity;
        this.dataCollector = queryBuilder.dataCollector;
        log.debug("Query formed as => {}", this);
    }

}
