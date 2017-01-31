package com.cdk.ea.data.query.interpreter;

import java.util.EnumSet;

import com.cdk.ea.data.core.NumberProperties;
import com.cdk.ea.data.exception.PropertiesInterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;
import com.cdk.ea.data.types.NumberType.NumberTypeBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NumberTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	NumberTypeBuilder numberTypeBuilder = new NumberTypeBuilder();
	EnumSet<NumberProperties> numberProps = EnumSet.noneOf(NumberProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream()
	    .map(NumberProperties::of)
	    .forEach(numberProps::add);
	} catch(Exception e) {
	    throw new PropertiesInterpretationException("Invalid Number Property. Possible Values are : "+NumberProperties.ENUM_MAP.keySet());
	}
	
	// default number type
	if(numberProps.isEmpty()) {
	    log.warn("No Number Properties specified. Defaulting to Integers.");
	    numberProps.add(NumberProperties.INTEGER);
	}

	numberTypeBuilder.setDataType(getDataType(identifiers));
	numberTypeBuilder.setTypeProperties(numberProps);
	numberTypeBuilder.setLength(getDataLength(identifiers));

	queryBuilder.setTypeBuilder(numberTypeBuilder);
    }

}
