package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.core.ListProperties;
import com.cdk.ea.data.exception.PropertiesInterpretationException;
import com.cdk.ea.data.exception.QueryInterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;
import com.cdk.ea.data.types.ListType.ListTypeBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	ListTypeBuilder listTypeBuilder = new ListTypeBuilder();
	EnumSet<ListProperties> listProps = EnumSet.noneOf(ListProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream()
	    .map(ListProperties::of)
	    .forEach(listProps::add);
	} catch(Exception e) {
	    throw new PropertiesInterpretationException("Invalid List Property. Possible Values are : "+ListProperties.ENUM_MAP.keySet());
	}

	listTypeBuilder.setDataType(getDataType(identifiers));
	listTypeBuilder.setTypeProperties(listProps);
	
	log.debug("List Properties set as : {}",listProps);

	if(listProps.contains(ListProperties.CUSTOM)) {
	    try {
		String customListDataIdentifier = StringUtils.substringBetween(Arrays.toString(identifiers), Constants.CUSTOM_LIST_VALS_PREFIX, Constants.CUSTOM_LIST_VALS_SUFFIX);
		String[] customListDataArr = StringUtils.split(customListDataIdentifier, Constants.COMMA);
		listTypeBuilder.setData(Arrays.stream(customListDataArr).filter(StringUtils::isNotBlank).collect(Collectors.toList()));
		log.debug("List Data set as : {}",Arrays.toString(customListDataArr));
	    } catch (Exception e) {
		throw new QueryInterpretationException("Define Elements for custom list between [...]");
	    }
	}

	queryBuilder.setTypeBuilder(listTypeBuilder);
    }

}
