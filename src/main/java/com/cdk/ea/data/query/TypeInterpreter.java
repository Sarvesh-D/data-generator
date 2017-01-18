package com.cdk.ea.data.query;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.core.ListProperties;
import com.cdk.ea.data.core.NumberProperties;
import com.cdk.ea.data.core.Properties;
import com.cdk.ea.data.core.StringProperties;
import com.cdk.ea.data.exception.InterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;
import com.cdk.ea.data.types.ListType.ListTypeBuilder;
import com.cdk.ea.data.types.NumberType.NumberTypeBuilder;
import com.cdk.ea.data.types.StringType.StringTypeBuilder;

class TypeInterpreter implements Interpreter {

    private static final int DEFAULT_LENGTH = 8;

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	DataType dataType = null;
	// Interpret Data Type
	try {
	    dataType = Arrays.stream(identifiers)
                		    .filter(i -> i.charAt(0) == Identifiers.TYPE.getIdentifier())
                		    .map(i -> DataType.of(i.charAt(1)))
                		    .findFirst()
		    .get();
	} catch(Exception e) {
	    throw new InterpretationException(typeErrorMessage());
	}

	// Interpret Data Properties
	Set<Character> propertyIdentifiers = Collections.EMPTY_SET;
	try {
	    propertyIdentifiers = Arrays.stream(identifiers)
                        		    .filter(i -> i.charAt(0) == Identifiers.PROPERTY.getIdentifier())
                        		    .map(i -> i.charAt(1))
                        		    .collect(Collectors.toSet());
	} catch(Exception e) {
	    throw new InterpretationException(propertyErrorMessage());
	}
	
	// Interpret Data Length
	int length = DEFAULT_LENGTH;
	try {
	    length = Arrays.stream(identifiers)
		    .filter(i -> i.charAt(0) == Identifiers.LENGTH.getIdentifier())
		    .map(i -> Integer.valueOf(i.substring(1)))
		    .findFirst()
		    .get();
	} catch(Exception e) {
	    // TODO handle giving default length set message
	} 

	switch (dataType) {
	case NUMBER:
	    NumberTypeBuilder numberTypeBuilder = new NumberTypeBuilder();
	    EnumSet<NumberProperties> numberProps = EnumSet.noneOf(NumberProperties.class);

	    try {
		propertyIdentifiers.stream()
                        		.map(NumberProperties::of)
                        		.forEach(numberProps::add);
	    } catch(Exception e) {
		throw new InterpretationException("Invalid Number Property. Possible Values are : "+NumberProperties.ENUM_MAP.keySet());
	    }

	    numberTypeBuilder.setDataType(dataType);
	    numberTypeBuilder.setTypeProperties(numberProps);
	    numberTypeBuilder.setLength(length);

	    queryBuilder.setTypeBuilder(numberTypeBuilder);
	    break;
	case STRING:
	    StringTypeBuilder stringTypeBuilder = new StringTypeBuilder();
	    EnumSet<StringProperties> stringProps = EnumSet.noneOf(StringProperties.class);

	    try {
		propertyIdentifiers.stream()
                        		.map(StringProperties::of)
                        		.forEach(stringProps::add);
	    } catch(Exception e) {
		throw new InterpretationException("Invalid String Property. Possible Values are : "+StringProperties.ENUM_MAP.keySet());
	    }

	    stringTypeBuilder.setDataType(dataType);
	    stringTypeBuilder.setTypeProperties(stringProps);
	    stringTypeBuilder.setLength(length);

	    queryBuilder.setTypeBuilder(stringTypeBuilder);
	    break;
	case LIST:
	    ListTypeBuilder listTypeBuilder = new ListTypeBuilder();
	    EnumSet<ListProperties> listProps = EnumSet.noneOf(ListProperties.class);
	    
	    try {
		propertyIdentifiers.stream()
                        		.map(ListProperties::of)
                        		.forEach(listProps::add);
	    } catch(Exception e) {
		throw new InterpretationException("Invalid List Property. Possible Values are : "+ListProperties.ENUM_MAP.keySet());
	    }
	    
	    listTypeBuilder.setDataType(dataType);
	    listTypeBuilder.setTypeProperties(listProps);
	   
	    if(listProps.contains(ListProperties.CUSTOM)) {
		try {
		String customListDataIdentifier = StringUtils.substringBetween(Arrays.toString(identifiers), "[[", "]]");
		String[] customListDataArr = StringUtils.split(customListDataIdentifier, ",");
		listTypeBuilder.setData(Arrays.asList(customListDataArr));
		} catch (Exception e) {
		    throw new InterpretationException("Define Elements for custom list between [...]");
		}
	    }
	    
	    queryBuilder.setTypeBuilder(listTypeBuilder);
	    break;
	}
    } 

    private String typeErrorMessage() {
	StringBuilder message = new StringBuilder();
	message.append("Invalid Type\n");
	message.append("Usage:- "+Identifiers.TYPE.getIdentifier()+"<type>\n");
	message.append("types:\n");
	message.append(DataType.ENUM_MAP);
	message.append("\n");
	return message.toString();
    }

    private String propertyErrorMessage() {
	StringBuilder message = new StringBuilder();
	message.append("Invalid Property\n");
	message.append("Usage:- "+Identifiers.PROPERTY.getIdentifier()+"<property>\n");
	message.append("properties:\n");
	message.append(Properties.ENUM_MAP);
	message.append("\n");
	return message.toString();
    }

}
