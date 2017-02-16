package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.EnumSet;
import java.util.Set;

import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * First Interpreter of chain of interpreters for interpreting query identifiers
 * and populating {@link QueryBuilder}.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class QueryInterpreter implements Interpreter {

    @Getter
    private static final QueryInterpreter instance = new QueryInterpreter();

    private static Set<Interpreters> queryInterpreters = EnumSet.noneOf(Interpreters.class);

    /**
     * Register all the interpreters in the chain. This will happen once for
     * each invocation of data-generator.
     */
    static {
	log.debug("registering query interpreters");
	queryInterpreters.add(Interpreters.TYPE_INTERPRETER);
	queryInterpreters.add(Interpreters.QUANTITY_INTERPRETER);
	queryInterpreters.add(Interpreters.DATA_COLLECTOR_INTERPRETER);
	log.debug("interpreters registered are : {}", queryInterpreters);
    }

    /**
     * {@inheritDoc}. Iterates over all registered interpreters invoking the
     * {@link Interpreter#doInterpret(QueryBuilder, String...)} for each.
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	queryInterpreters.stream().forEach(interpreter -> interpreter.get().doInterpret(queryBuilder, identifiers));
    }

}
