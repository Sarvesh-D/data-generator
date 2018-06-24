package com.ds.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.Constants;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.ListProperties;
import com.ds.tools.data.generator.exception.PropertiesInterpretationException;
import com.ds.tools.data.generator.exception.QueryInterpretationException;
import com.ds.tools.data.generator.query.Query.QueryBuilder;
import com.ds.tools.data.generator.types.ListType;
import com.ds.tools.data.generator.types.ListType.ListTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link ListType} from identifiers.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
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
    public void doInterpret(final QueryBuilder queryBuilder, final String query) {
        final ListTypeBuilder listTypeBuilder = new ListTypeBuilder();
        final EnumSet<ListProperties> listProps = EnumSet.noneOf(ListProperties.class);

        try {
            getPropertyIdentifiers(query).stream()
                                         .map(com.ds.tools.data.generator.common.StringUtils::firstCharacterOf)
                                         .map(ListProperties::of)
                                         .forEach(listProps::add);
        } catch (final Exception e) {
            throw new PropertiesInterpretationException("Invalid List Property. Possible Values are : " + ListProperties.getEnumMap()
                                                                                                                        .keySet());
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
                final String customListDataIdentifier = StringUtils.substringBetween(query, Constants.CUSTOM_LIST_VALS_PREFIX, Constants.CUSTOM_LIST_VALS_SUFFIX);
                final String[] customListDataArr = StringUtils.split(customListDataIdentifier, Constants.COMMA);
                listTypeBuilder.setData(Arrays.stream(customListDataArr)
                                              .map(StringUtils::trim)
                                              .filter(StringUtils::isNotBlank)
                                              .collect(Collectors.toList()));
                log.debug("List Data set as : {}", listTypeBuilder.getData());
            } catch (final Exception e) {
                throw new QueryInterpretationException("Define Elements for custom list between [[...]]");
            }
        }

        queryBuilder.setTypeBuilder(listTypeBuilder);
    }

}
