package com.cdk.ea.data.query.interpreter;

import java.util.HashSet;
import java.util.Set;

import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryInterpreter implements Interpreter {
    
    private Set<Interpreter> queryInterpreters = new HashSet<>();

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	regiesterQueryInterpreters();
	queryInterpreters.stream().forEach(interpreter -> interpreter.doInterpret(queryBuilder, identifiers));
    }
    
    private void regiesterQueryInterpreters() {
	log.debug("registering query interpreters");
	queryInterpreters.add(new TypeInterpreter());
	queryInterpreters.add(new QuantityInterpreter());
	queryInterpreters.add(new DataCollectorInterpreter());
	log.debug("interpreters registered are : {}",queryInterpreters);
    }

}
