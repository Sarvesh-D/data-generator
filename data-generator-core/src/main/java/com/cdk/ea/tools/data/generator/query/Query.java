package com.cdk.ea.tools.data.generator.query;

import com.cdk.ea.tools.data.generator.common.Builder;
import com.cdk.ea.tools.data.generator.generators.DataCollector;
import com.cdk.ea.tools.data.generator.query.interpreter.Interpreter;
import com.cdk.ea.tools.data.generator.query.interpreter.Interpreters;
import com.cdk.ea.tools.data.generator.types.Type;
import com.cdk.ea.tools.data.generator.types.TypeBuilder;
import com.cdk.ea.tools.data.generator.types.TypeProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value class for holding a single DataGeneration Query
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see QueryBuilder
 */
@Getter
@ToString
@Slf4j
public final class Query {

    /**
     * Helper Builder class for building {@link Query} object
     * 
     * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
     * @since 10-02-2017
     * @version 1.0
     */
    @Setter
    @Slf4j
    public static class QueryBuilder implements Builder<Query, String> {

	private TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;
	private int quantity;
	private DataCollector dataCollector;

	@Override
	public Query build(String query) {
	    log.debug("Building Query Instance from params {}", query);
	    QueryBuilder queryBuilder = new QueryBuilder();
	    Interpreter queryInterpreter = Interpreters.QUERY_INTERPRETER.get();
	    queryInterpreter.doInterpret(queryBuilder, query.toString());
	    return new Query(queryBuilder);
	}

    }

    private final TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;
    private final int quantity;

    private final DataCollector dataCollector;

    private Query(QueryBuilder queryBuilder) {
	this.typeBuilder = queryBuilder.typeBuilder;
	this.quantity = queryBuilder.quantity;
	this.dataCollector = queryBuilder.dataCollector;
	log.debug("Query formed as => {}", this);
    }

}
