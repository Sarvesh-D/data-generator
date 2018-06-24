package com.ds.tools.data.generator.query.interpreter;

import com.ds.tools.data.generator.query.Query;
import com.ds.tools.data.generator.query.Query.QueryBuilder;

/**
 * Root interface to be implemented by any Interpreter.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see DataCollectorInterpreter
 * @see GlobalDefaultOverrideInterpreter
 * @see QuantityInterpreter
 * @see QueryInterpreter
 * @see TypeInterpreter
 */
public interface Interpreter {

    /**
     * Interprets the identifiers and populates the {@link QueryBuilder}
     *
     * @param queryBuilder
     *            to be populated
     * @param query
     *            containing {@link Query} details
     */
    default void doInterpret(final QueryBuilder queryBuilder, final String query) {
        return;
    }

}
