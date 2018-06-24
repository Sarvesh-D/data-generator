package com.ds.tools.data.generator.query.interpreter;

import java.util.EnumSet;

import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.NumberProperties;
import com.ds.tools.data.generator.exception.PropertiesInterpretationException;
import com.ds.tools.data.generator.query.Query.QueryBuilder;
import com.ds.tools.data.generator.types.NumberType;
import com.ds.tools.data.generator.types.NumberType.NumberTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link NumberType} from
 * identifiers.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
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
    public void doInterpret(final QueryBuilder queryBuilder, final String query) {
        final NumberTypeBuilder numberTypeBuilder = new NumberTypeBuilder();
        final EnumSet<NumberProperties> numberProps = EnumSet.noneOf(NumberProperties.class);

        try {
            getPropertyIdentifiers(query).stream()
                                         .map(com.ds.tools.data.generator.common.StringUtils::firstCharacterOf)
                                         .map(NumberProperties::of)
                                         .forEach(numberProps::add);
        } catch (final Exception e) {
            throw new PropertiesInterpretationException("Invalid Number Property. Possible Values are : " + NumberProperties.getEnumMap()
                                                                                                                            .keySet());
        }

        // default number type
        if (numberProps.isEmpty()) {
            log.warn("No Number Properties specified. Defaulting to Integers.");
            numberProps.add(Defaults.DEFAULT_NUMBER_PROP);
        }

        numberTypeBuilder.setDataType(getDataType(query));
        numberTypeBuilder.setTypeProperties(numberProps);
        numberTypeBuilder.setLength(getDataLength(query));

        queryBuilder.setTypeBuilder(numberTypeBuilder);
    }

}
