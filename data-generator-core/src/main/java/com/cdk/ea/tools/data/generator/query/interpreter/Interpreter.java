package com.cdk.ea.tools.data.generator.query.interpreter;

import com.cdk.ea.tools.data.generator.query.Query;
import com.cdk.ea.tools.data.generator.query.Query.QueryBuilder;

/**
 * Root interface to be implemented by any Interpreter.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
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