package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.Defaults;
import com.cdk.ea.tools.data.generation.core.ListProperties;
import com.cdk.ea.tools.data.generation.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generation.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generation.types.ListType;
import com.cdk.ea.tools.data.generation.types.ListType.ListTypeBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link ListType} from identifiers.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 11-02-2017
 * @version 1.0
 * @see ListType
 * @see ListTypeBuilder
 */
@Slf4j
public class ListTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

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
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	ListTypeBuilder listTypeBuilder = new ListTypeBuilder();
	EnumSet<ListProperties> listProps = EnumSet.noneOf(ListProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream().map(ListProperties::of).forEach(listProps::add);
	} catch (Exception e) {
	    throw new PropertiesInterpretationException(
		    "Invalid List Property. Possible Values are : " + ListProperties.getEnumMap().keySet());
	}

	// default list properties
	if (listProps.isEmpty()) {
	    log.warn("No List properties specifed. Defaulting to CUSTOM List");
	    listProps.add(Defaults.DEFAULT_LIST_PROP);
	}

	listTypeBuilder.setDataType(getDataType(identifiers));
	listTypeBuilder.setTypeProperties(listProps);

	log.debug("List Properties set as : {}", listProps);

	if (listProps.contains(ListProperties.CUSTOM)) {
	    try {
		String customListDataIdentifier = StringUtils.substringBetween(Arrays.toString(identifiers),
			Constants.CUSTOM_LIST_VALS_PREFIX, Constants.CUSTOM_LIST_VALS_SUFFIX);
		String[] customListDataArr = StringUtils.split(customListDataIdentifier, Constants.COMMA);
		listTypeBuilder.setData(
			Arrays.stream(customListDataArr).filter(StringUtils::isNotBlank).collect(Collectors.toList()));
		log.debug("List Data set as : {}", Arrays.toString(customListDataArr));
	    } catch (Exception e) {
		throw new QueryInterpretationException("Define Elements for custom list between [[...]]");
	    }
	}

	queryBuilder.setTypeBuilder(listTypeBuilder);
    }

}
