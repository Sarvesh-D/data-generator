package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.Collections;
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
	try {
	    return Arrays.stream(queryParams)
                		    .filter(i -> i.charAt(0) == Identifiers.TYPE.getIdentifier())
                		    .map(i -> DataType.of(i.charAt(1)))
                		    .findFirst()
		    .get();
	} catch(Exception e) {
	    throw new TypeInterpretationException();
	}
    }
    
    public static int getDataLength(String... queryParams) {
	int length = DEFAULT_LENGTH;
	try {
	    length = Arrays.stream(queryParams)
        		    .filter(i -> i.charAt(0) == Identifiers.LENGTH.getIdentifier())
        		    .map(i -> Integer.valueOf(i.substring(1)))
        		    .findFirst()
        		    .get();
	    return length > 0 ? length : DEFAULT_LENGTH; 
	} catch(Exception e) {
	    log.warn("Error occured while interpreting length for data : {}. Default length [{}] shall be used",e.getMessage(), DEFAULT_LENGTH);
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
	    throw new PropertiesInterpretationException();
	}
    }
    
}
