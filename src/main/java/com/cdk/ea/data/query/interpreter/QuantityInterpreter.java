package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.query.Query.QueryBuilder;

class QuantityInterpreter implements Interpreter {
    
    private static final int DEFAULT_QUANTITY = 100;

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	int quantity = DEFAULT_QUANTITY;
	try {
	    quantity = Arrays.stream(identifiers)
	    		.filter(i -> i.charAt(0) == Identifiers.QUANTITY.getIdentifier())
	    		.map(i -> Integer.valueOf(i.substring(1)))
	    		.findFirst()
	    		.get();
	} catch(Exception e) {
	    quantity = DEFAULT_QUANTITY;
	} finally {
	    queryBuilder.setQuantity(quantity);
	}
    }

}
