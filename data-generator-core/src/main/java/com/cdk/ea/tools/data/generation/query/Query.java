package com.cdk.ea.tools.data.generation.query;

import java.util.Arrays;

import com.cdk.ea.tools.data.generation.common.Builder;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.query.interpreter.Interpreter;
import com.cdk.ea.tools.data.generation.query.interpreter.Interpreters;
import com.cdk.ea.tools.data.generation.types.Type;
import com.cdk.ea.tools.data.generation.types.TypeBuilder;
import com.cdk.ea.tools.data.generation.types.TypeProperties;

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
    public static class QueryBuilder implements Builder<Query> {

	private TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;
	private int quantity;
	private DataCollector dataCollector;

	@Override
	public Query build(String... queryParams) {
	    log.debug("Building Query Instance from params {}", Arrays.toString(queryParams));
	    QueryBuilder queryBuilder = new QueryBuilder();
	    Interpreter queryInterpreter = Interpreters.QUERY_INTERPRETER.get();
	    queryInterpreter.doInterpret(queryBuilder, queryParams);
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
