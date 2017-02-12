package com.cdk.ea.tools.data.generation.query.interpreter;

import com.cdk.ea.tools.data.generation.query.Query;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;

/**
 * Root interface to be implemented by any Interpreter.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
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
     * @param identifiers
     *            containing {@link Query} details
     */
    default void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	return;
    }

}
