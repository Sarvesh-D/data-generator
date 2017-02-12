package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.EnumSet;

import com.cdk.ea.tools.data.generation.core.Defaults;
import com.cdk.ea.tools.data.generation.core.NumberProperties;
import com.cdk.ea.tools.data.generation.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generation.types.NumberType;
import com.cdk.ea.tools.data.generation.types.NumberType.NumberTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link NumberType} from
 * identifiers.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 11-02-2017
 * @version 1.0
 * @see NumberType
 * @see NumberTypeBuilder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class NumberTypeInterpreter extends AbstractTypeInterpreter {

    /**
     * Interprets and populates the {@link NumberTypeBuilder} and attaches it to
     * {@link QueryBuilder}
     * 
     * @throws PropertiesInterpretationException
     *             if invalid {@link NumberProperties} are found
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	NumberTypeBuilder numberTypeBuilder = new NumberTypeBuilder();
	EnumSet<NumberProperties> numberProps = EnumSet.noneOf(NumberProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream().map(NumberProperties::of).forEach(numberProps::add);
	} catch (Exception e) {
	    throw new PropertiesInterpretationException(
		    "Invalid Number Property. Possible Values are : " + NumberProperties.getEnumMap().keySet());
	}

	// default number type
	if (numberProps.isEmpty()) {
	    log.warn("No Number Properties specified. Defaulting to Integers.");
	    numberProps.add(Defaults.DEFAULT_NUMBER_PROP);
	}

	numberTypeBuilder.setDataType(getDataType(identifiers));
	numberTypeBuilder.setTypeProperties(numberProps);
	numberTypeBuilder.setLength(getDataLength(identifiers));

	queryBuilder.setTypeBuilder(numberTypeBuilder);
    }

}
