package com.cdk.ea.tools.data.generation.query.interpreter;

import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;

interface Interpreter {

    default void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	return;
    }

}
