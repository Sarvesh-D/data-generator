package com.cdk.ea.data.query;

import com.cdk.ea.data.query.Query.QueryBuilder;

interface Interpreter {
    
    void doInterpret(QueryBuilder queryBuilder, String... identifiers);

}
