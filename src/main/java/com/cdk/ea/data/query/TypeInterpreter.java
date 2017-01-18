package com.cdk.ea.data.query;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.query.Query.QueryBuilder;

class TypeInterpreter implements Interpreter {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	DataType dataType = AbstractTypeInterpretationStrategy.getDataType(identifiers);
	try {
	    dataType.getTypeInterpretationStrategy().newInstance().doInterpret(queryBuilder, identifiers);
	} catch (InstantiationException | IllegalAccessException e) {
	    // TODO Handle this.
	    e.printStackTrace();
	}
    } 

}
