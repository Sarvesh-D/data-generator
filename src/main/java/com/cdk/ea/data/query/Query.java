package com.cdk.ea.data.query;

import java.util.Arrays;

import com.cdk.ea.data.common.Builder;
import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.query.interpreter.QueryInterpreter;
import com.cdk.ea.data.types.Type;
import com.cdk.ea.data.types.TypeBuilder;
import com.cdk.ea.data.types.TypeProperties;

import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public final class Query {
    
    @Getter private final TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;
    @Getter private final int quantity;
    @Getter private final DataCollector dataCollector;
    
    private Query(QueryBuilder queryBuilder) {
	this.typeBuilder = queryBuilder.typeBuilder;
	this.quantity = queryBuilder.quantity;
	this.dataCollector = queryBuilder.dataCollector;
	log.debug("Query formed as => {}",this);
    }
    
    @Data
    @Slf4j
    public static class QueryBuilder implements Builder<Query> {
	
	private TypeBuilder<? extends Type, ? extends TypeProperties> typeBuilder;
	private int quantity;
	private DataCollector dataCollector;

	@Override
	public Query build(String... queryParams) {
	    log.debug("Building Query Instance from params {}",Arrays.toString(queryParams));
	    QueryBuilder queryBuilder = new QueryBuilder();
	    QueryInterpreter queryInterpreter = new QueryInterpreter();
	    queryInterpreter.doInterpret(queryBuilder, queryParams);
	    return new Query(queryBuilder);
	}
	
    }
    
}
