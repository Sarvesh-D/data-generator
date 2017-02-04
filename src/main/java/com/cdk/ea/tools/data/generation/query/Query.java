package com.cdk.ea.tools.data.generation.query;

import java.util.Arrays;

import com.cdk.ea.tools.data.generation.common.Builder;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.query.interpreter.QueryInterpreter;
import com.cdk.ea.tools.data.generation.types.Type;
import com.cdk.ea.tools.data.generation.types.TypeBuilder;
import com.cdk.ea.tools.data.generation.types.TypeProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public final class Query {

    private final TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;
    private final int quantity;
    private final DataCollector dataCollector;

    private Query(QueryBuilder queryBuilder) {
	this.typeBuilder = queryBuilder.typeBuilder;
	this.quantity = queryBuilder.quantity;
	this.dataCollector = queryBuilder.dataCollector;
	log.debug("Query formed as => {}", this);
    }

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
	    QueryInterpreter queryInterpreter = new QueryInterpreter();
	    queryInterpreter.doInterpret(queryBuilder, queryParams);
	    return new Query(queryBuilder);
	}

    }

}
