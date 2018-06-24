package com.ds.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.Constants;
import com.ds.tools.data.generator.core.RegexProperties;
import com.ds.tools.data.generator.exception.PropertiesInterpretationException;
import com.ds.tools.data.generator.exception.QueryInterpretationException;
import com.ds.tools.data.generator.query.Query.QueryBuilder;
import com.ds.tools.data.generator.types.RegexType;
import com.ds.tools.data.generator.types.RegexType.RegexTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link RegexType} from
 * identifiers.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see RegexType
 * @see RegexTypeBuilder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class RegexTypeInterpreter extends AbstractTypeInterpreter {

    /**
     * Interprets and populates the {@link RegexTypeBuilder} and attaches it to
     * {@link QueryBuilder}
     *
     * @throws PropertiesInterpretationException
     *             if invalid {@link RegexProperties} are found
     * @throws QueryInterpretationException
     *             if no regex is defined for {@link RegexType}
     */
    @Override
    public void doInterpret(final QueryBuilder queryBuilder, final String query) {
        final RegexTypeBuilder regexTypeBuilder = new RegexTypeBuilder();
        final EnumSet<RegexProperties> regexProps = EnumSet.noneOf(RegexProperties.class);

        try {
            getPropertyIdentifiers(query).stream()
                                         .map(com.ds.tools.data.generator.common.StringUtils::firstCharacterOf)
                                         .map(RegexProperties::of)
                                         .forEach(regexProps::add);
        } catch (final Exception e) {
            throw new PropertiesInterpretationException("Invalid Regex Property. Possible Values are : " + RegexProperties.getEnumMap()
                                                                                                                          .keySet());
        }

        regexTypeBuilder.setDataType(getDataType(query));
        regexTypeBuilder.setTypeProperties(regexProps);
        try {
            final String regex = StringUtils.substringBetween(Arrays.toString(StringUtils.split(query)), Constants.REGEX_EXPR_PREFIX, Constants.REGEX_EXPR_SUFFIX);
            log.debug("Setting regex as {}", regex);
            regexTypeBuilder.setRegex(regex);
        } catch (final Exception e) {
            throw new QueryInterpretationException("Define regex string between {{...}}");
        }

        queryBuilder.setTypeBuilder(regexTypeBuilder);
    }

}
