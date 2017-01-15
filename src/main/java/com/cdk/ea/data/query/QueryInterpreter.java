package com.cdk.ea.data.query;

import java.util.HashSet;
import java.util.Set;

import com.cdk.ea.data.query.Query.QueryBuilder;

class QueryInterpreter implements Interpreter {
    
    private Set<Interpreter> queryInterpreters = new HashSet<>();

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	regiesterQueryInterpreters();
	queryInterpreters.stream().forEach(interpreter -> interpreter.doInterpret(queryBuilder, identifiers));
    }
    
    private void regiesterQueryInterpreters() {
	queryInterpreters.add(new TypeInterpreter());
	queryInterpreters.add(new QuantityInterpreter());
    }

}
