package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.NonNull;
import lombok.Setter;

class QuantityInterpreter implements Interpreter {

    @NonNull
    @Setter 
    private static Integer defaultQuantity = 100;

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	int quantity = defaultQuantity;
	try {
	    // allow local quantity to override the default quantity
	    quantity = Arrays.stream(identifiers)
	    		.filter(i -> i.charAt(0) == Identifiers.QUANTITY.getIdentifier())
	    		.map(i -> Integer.valueOf(i.substring(1)))
	    		.findFirst()
	    		.get();
	} catch(Exception e) {
	    quantity = defaultQuantity;
	} finally {
	    queryBuilder.setQuantity(quantity);
	}
    }
    

}
