package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Assert;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GlobalDefaultOverrideInterpreter implements Interpreter {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... overrideIdentifiers) {
	Assert.assertNull("QueryBuilder supplied to GlobalPropertyOverrideInterpreter should always be null", queryBuilder);
	/*
	This interpreters sole job is to override few of the default
	settings. These default settings overriden by this interpreter
	applies to all the queries, hence these should not be set 
	individually in the queryBuilder. These properties of corresponding
	interceptor should be set/overriden instead. 
	*/
	
	Optional<Integer> quantityOverride = Arrays.stream(overrideIdentifiers)
				.filter(i -> i.charAt(0) == Identifiers.QUANTITY.getIdentifier())
				.map(i -> Integer.valueOf(i.substring(1)))
		    		.findFirst();
	if(quantityOverride.isPresent()) {
	    QuantityInterpreter.setDefaultQuantity(quantityOverride.get());
	    log.debug("Default quantity overriden to [{}]", quantityOverride.get());
	}

    }

}
