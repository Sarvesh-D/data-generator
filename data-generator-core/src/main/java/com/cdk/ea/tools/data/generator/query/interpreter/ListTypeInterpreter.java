package com.cdk.ea.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.core.Defaults;
import com.cdk.ea.tools.data.generator.core.ListProperties;
import com.cdk.ea.tools.data.generator.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generator.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generator.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generator.types.ListType;
import com.cdk.ea.tools.data.generator.types.ListType.ListTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link ListType} from identifiers.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see ListType
 * @see ListTypeBuilder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class ListTypeInterpreter extends AbstractTypeInterpreter {

    /**
     * Interprets and populates the {@link ListTypeBuilder} and attaches it to
     * {@link QueryBuilder}
     * 
     * @throws PropertiesInterpretationException
     *             if invalid {@link ListProperties} are found
     * @throws QueryInterpretationException
     *             if data for {@link ListType} is not present.
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String query) {
	ListTypeBuilder listTypeBuilder = new ListTypeBuilder();
	EnumSet<ListProperties> listProps = EnumSet.noneOf(ListProperties.class);

	try {
	    getPropertyIdentifiers(query).stream().map(com.cdk.ea.tools.data.generator.common.StringUtils::firstCharacterOf).map(ListProperties::of).forEach(listProps::add);
	} catch (Exception e) {
	    throw new PropertiesInterpretationException(
		    "Invalid List Property. Possible Values are : " + ListProperties.getEnumMap().keySet());
	}

	// default list properties
	if (listProps.isEmpty()) {
	    log.warn("No List properties specifed. Defaulting to CUSTOM List");
	    listProps.add(Defaults.DEFAULT_LIST_PROP);
	}

	listTypeBuilder.setDataType(getDataType(query));
	listTypeBuilder.setTypeProperties(listProps);

	log.debug("List Properties set as : {}", listProps);

	if (listProps.contains(ListProperties.CUSTOM)) {
	    try {
		String customListDataIdentifier = StringUtils.substringBetween(query, Constants.CUSTOM_LIST_VALS_PREFIX,
			Constants.CUSTOM_LIST_VALS_SUFFIX);
		String[] customListDataArr = StringUtils.split(customListDataIdentifier, Constants.COMMA);
		listTypeBuilder.setData(Arrays.stream(customListDataArr).map(StringUtils::trim)
			.filter(StringUtils::isNotBlank).collect(Collectors.toList()));
		log.debug("List Data set as : {}", listTypeBuilder.getData());
	    } catch (Exception e) {
		throw new QueryInterpretationException("Define Elements for custom list between [[...]]");
	    }
	}

	queryBuilder.setTypeBuilder(listTypeBuilder);
    }

}
