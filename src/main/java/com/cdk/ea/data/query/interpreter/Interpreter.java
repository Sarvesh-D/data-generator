package com.cdk.ea.data.query.interpreter;

import com.cdk.ea.data.query.Query.QueryBuilder;

interface Interpreter {
    
    default void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	return;
    }

}
