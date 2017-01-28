package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class QuantityInterpreter implements Interpreter {

    @NonNull
    @Setter 
    private static Integer defaultQuantity = 100;

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	try {
	    // allow local quantity to override the default quantity
	    Optional<Integer> quantity = Arrays.stream(identifiers)
                        	    		.filter(i -> i.charAt(0) == Identifiers.QUANTITY.getIdentifier())
                        	    		.map(i -> Integer.valueOf(i.substring(1)))
                        	    		.findFirst();
	    if(quantity.isPresent())
		queryBuilder.setQuantity(quantity.get());
	    else {
		log.debug("Quantity not specified. Default quantity of {} shall be used",defaultQuantity);
		queryBuilder.setQuantity(defaultQuantity);
	    }
	} catch(Exception e) {
	    log.warn("Error occured while interpreting quantity : {}. Default quantity of {} shall be used",e.getMessage(),defaultQuantity);
	    queryBuilder.setQuantity(defaultQuantity);
	}
    }
    

}
