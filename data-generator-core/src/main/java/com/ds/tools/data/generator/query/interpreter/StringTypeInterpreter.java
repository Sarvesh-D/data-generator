package com.ds.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.Constants;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.Identifiers;
import com.ds.tools.data.generator.core.StringProperties;
import com.ds.tools.data.generator.exception.PropertiesInterpretationException;
import com.ds.tools.data.generator.exception.QueryInterpretationException;
import com.ds.tools.data.generator.query.Query.QueryBuilder;
import com.ds.tools.data.generator.types.StringType;
import com.ds.tools.data.generator.types.StringType.StringTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link StringType} from
 * identifiers.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see StringType
 * @see StringTypeBuilder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class StringTypeInterpreter extends AbstractTypeInterpreter {

    /**
     * Interprets and populates the {@link StringTypeBuilder} and attaches it to
     * {@link QueryBuilder}
     *
     * @throws PropertiesInterpretationException
     *             if invalid {@link StringProperties} are found
     * @throws QueryInterpretationException
     *             if baseString length is greater then finalString length in case
     *             Prefix and/or Suffix are used.
     */
    @Override
    public void doInterpret(final QueryBuilder queryBuilder, final String query) {
        final StringTypeBuilder stringTypeBuilder = new StringTypeBuilder();
        final EnumSet<StringProperties> stringProps = EnumSet.noneOf(StringProperties.class);

        try {
            getPropertyIdentifiers(query).stream()
                                         .map(com.ds.tools.data.generator.common.StringUtils::firstCharacterOf)
                                         .map(StringProperties::of)
                                         .forEach(stringProps::add);
        } catch (final Exception e) {
            throw new PropertiesInterpretationException("Invalid String Property. Possible Values are : " + StringProperties.getEnumMap()
                                                                                                                            .keySet());
        }

        // default string type
        if (stringProps.isEmpty()) {
            log.warn("No String Properties specified. Defaulting to ALPHA String.");
            stringProps.add(Defaults.DEFAULT_STRING_PROP);
        }

        final String prefix = getPrefix(query);
        final String suffix = getSuffix(query);
        final int dataLength = getDataLength(query);

        final int baseStringLength = prefix.length() + suffix.length();
        if (dataLength < baseStringLength) {
            throw new QueryInterpretationException(String.format("Total String length [%d] should be greater or equal to Base String length [%d]", dataLength, baseStringLength));
        }

        stringTypeBuilder.setDataType(getDataType(query));
        stringTypeBuilder.setTypeProperties(stringProps);
        stringTypeBuilder.setLength(dataLength - baseStringLength);
        stringTypeBuilder.setPrefix(prefix);
        stringTypeBuilder.setSuffix(suffix);

        queryBuilder.setTypeBuilder(stringTypeBuilder);
    }

    /**
     * Identifies the Prefix for the StringType
     *
     * @param query
     *            to identify prefix
     * @return prefix
     */
    private String getPrefix(final String query) {
        final Optional<String> prefix = Arrays.stream(StringUtils.split(query))
                                              .filter(i -> i.charAt(0) == Identifiers.PREFIX.getIdentifier())
                                              .map(i -> i.substring(1))
                                              .findFirst();

        return prefix.isPresent() ? StringUtils.trimToEmpty(prefix.get()) : Constants.EMPTY_STRING;
    }

    /**
     * Identifies the Suffix for the StringType
     *
     * @param query
     *            to identify suffix
     * @return suffix
     */
    private String getSuffix(final String query) {
        final Optional<String> suffix = Arrays.stream(StringUtils.split(query))
                                              .filter(i -> i.charAt(0) == Identifiers.SUFFIX.getIdentifier())
                                              .map(i -> i.substring(1))
                                              .findFirst();

        return suffix.isPresent() ? StringUtils.trimToEmpty(suffix.get()) : Constants.EMPTY_STRING;
    }

}
