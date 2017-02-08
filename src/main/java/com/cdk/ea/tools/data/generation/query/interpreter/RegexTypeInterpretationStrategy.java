package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.RegexProperties;
import com.cdk.ea.tools.data.generation.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generation.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generation.types.RegexType.RegexTypeBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RegexTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	RegexTypeBuilder regexTypeBuilder = new RegexTypeBuilder();
	EnumSet<RegexProperties> regexProps = EnumSet.noneOf(RegexProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream().map(RegexProperties::of).forEach(regexProps::add);
	} catch (Exception e) {
	    throw new PropertiesInterpretationException(
		    "Invalid Regex Property. Possible Values are : " + RegexProperties.ENUM_MAP.keySet());
	}

	regexTypeBuilder.setDataType(getDataType(identifiers));
	regexTypeBuilder.setTypeProperties(regexProps);
	try {
	    String regex = StringUtils.substringBetween(Arrays.toString(identifiers), Constants.REGEX_EXPR_PREFIX,
		    Constants.REGEX_EXPR_SUFFIX);
	    log.debug("Setting regex as {}", regex);
	    regexTypeBuilder.setRegex(regex);
	} catch (Exception e) {
	    throw new QueryInterpretationException("Define regex string between {{...}}");
	}

	queryBuilder.setTypeBuilder(regexTypeBuilder);
    }

}