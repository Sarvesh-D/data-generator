package com.cdk.ea.tools.data.generation.query.interpreter;

import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class TypeInterpreter implements Interpreter {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	try {
	    DataType dataType = AbstractTypeInterpretationStrategy.getDataType(identifiers);
	    log.debug("DataType set as : {}", dataType);
	    dataType.getTypeInterpretationStrategy().newInstance().doInterpret(queryBuilder, identifiers);
	} catch (InstantiationException | IllegalAccessException e) {
	    log.error("Error occurred while interpreting type : {}", e.getMessage());
	}
    }

}
