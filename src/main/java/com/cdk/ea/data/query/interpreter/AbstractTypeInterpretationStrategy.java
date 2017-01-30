package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.exception.PropertiesInterpretationException;
import com.cdk.ea.data.exception.TypeInterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTypeInterpretationStrategy {
    
    private static final int DEFAULT_LENGTH = 8;
    
    public abstract void doInterpret(QueryBuilder queryBuilder, String... identifiers);
    
    public static DataType getDataType(String... queryParams) {
	Optional<DataType> dataType;
	try {
	    dataType = Arrays.stream(queryParams)
		    .filter(i -> i.charAt(0) == Identifiers.TYPE.getIdentifier())
		    .map(i -> DataType.of(i.charAt(1)))
		    .findFirst();
	} catch(Exception e) {
	    throw new TypeInterpretationException("Invalid Data Type Specified");
	}
	
	if(dataType.isPresent()) 
	    return dataType.get();
	else throw new TypeInterpretationException("No Data Type Specified.");
    }
    
    public static int getDataLength(String... queryParams) {
	try {
	    Optional<Integer> length = Arrays.stream(queryParams)
                        		    .filter(i -> i.charAt(0) == Identifiers.LENGTH.getIdentifier())
                        		    .map(i -> Integer.valueOf(i.substring(1)))
                        		    .findFirst();
	    if(length.isPresent())
		return length.get() > 0 ? length.get() : DEFAULT_LENGTH;
	    else {
		log.debug("Length not specified. Default length [{}] shall be used",DEFAULT_LENGTH);
		return DEFAULT_LENGTH;
	    }
	} catch(Exception e) {
	    log.warn("Error occured while interpreting length : {}. Default length of {} shall be used",e.getMessage(),DEFAULT_LENGTH);
	    return DEFAULT_LENGTH;
	}
    }
    
    public static Set<Character> getPropertyIdentifiers(String... queryParams) {
	Set<Character> propertyIdentifiers = Collections.EMPTY_SET;
	try {
	    propertyIdentifiers = Arrays.stream(queryParams)
                        		    .filter(i -> i.charAt(0) == Identifiers.PROPERTY.getIdentifier())
                        		    .map(i -> i.charAt(1))
                        		    .collect(Collectors.toSet());
	    return propertyIdentifiers;
	} catch(Exception e) {
	    throw new PropertiesInterpretationException(e.getMessage());
	}
    }
    
}
