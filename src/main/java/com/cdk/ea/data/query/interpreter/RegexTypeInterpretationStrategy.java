package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.core.RegexProperties;
import com.cdk.ea.data.exception.InterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;
import com.cdk.ea.data.types.RegexType.RegexTypeBuilder;

public class RegexTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	RegexTypeBuilder regexTypeBuilder = new RegexTypeBuilder();
	EnumSet<RegexProperties> regexProps = EnumSet.noneOf(RegexProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream()
	    .map(RegexProperties::of)
	    .forEach(regexProps::add);
	} catch(Exception e) {
	    throw new InterpretationException("Invalid Regex Property. Possible Values are : "+RegexProperties.ENUM_MAP.keySet());
	}

	regexTypeBuilder.setDataType(getDataType(identifiers));
	regexTypeBuilder.setTypeProperties(regexProps);
	try {
	    String regex = StringUtils.substringBetween(Arrays.toString(identifiers), Constants.REGEX_EXPR_PREFIX, Constants.REGEX_EXPR_SUFFIX);
	    regexTypeBuilder.setRegex(regex);
	} catch(Exception e) {
	    throw new InterpretationException("Define regex string between {...}");
	}

	queryBuilder.setTypeBuilder(regexTypeBuilder);
    }

}
