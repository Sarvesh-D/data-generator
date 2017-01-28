package com.cdk.ea.data.query.interpreter;

import java.util.EnumSet;
import java.util.Set;

import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryInterpreter implements Interpreter {
    
    private static Set<Interpreters> queryInterpreters = EnumSet.noneOf(Interpreters.class);
    
    static {
	log.debug("registering query interpreters");
	    queryInterpreters.add(Interpreters.TYPE_INTERPRETER);
	    queryInterpreters.add(Interpreters.QUANTITY_INTERPRETER);
	    queryInterpreters.add(Interpreters.DATA_COLLECTOR_INTERPRETER);
	log.debug("interpreters registered are : {}",queryInterpreters);
    }

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	queryInterpreters.stream().forEach(interpreter -> interpreter.get().doInterpret(queryBuilder, identifiers));
    }
    
}
