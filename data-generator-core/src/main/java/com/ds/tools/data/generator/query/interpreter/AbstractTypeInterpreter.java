package com.ds.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.Identifiers;
import com.ds.tools.data.generator.core.Properties;
import com.ds.tools.data.generator.exception.PropertiesInterpretationException;
import com.ds.tools.data.generator.exception.QueryInterpretationException;
import com.ds.tools.data.generator.exception.TypeInterpretationException;
import com.ds.tools.data.generator.query.Query.QueryBuilder;
import com.ds.tools.data.generator.types.Type;

import lombok.extern.slf4j.Slf4j;

/**
 * Abstract class for interpreting {@link DataType}, Data Length and
 * {@link Properties} from the query identifiers. This Abstract class is to be
 * implemented by implementations of class returned by
 * {@link DataType#getTypeInterpretationStrategy()}.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see ListTypeInterpreter
 * @see NumberTypeInterpreter
 * @see RegexTypeInterpreter
 * @see StringTypeInterpreter
 */
@Slf4j
abstract class AbstractTypeInterpreter implements Interpreter {

    private static final int DEFAULT_LENGTH = Defaults.DEFAULT_LENGTH;

    /**
     * Interprets the length of single data record from the identifiers. This method
     * returns {@link Defaults#DEFAULT_LENGTH} if no length is specified.
     *
     * @param query
     *            to identify the length of single data record
     * @return length of single data record.
     */
    public static int getDataLength(final String query) {
        try {
            final Optional<Integer> length = Arrays.stream(StringUtils.split(query))
                                                   .filter(i -> i.charAt(0) == Identifiers.LENGTH.getIdentifier())
                                                   .map(i -> Integer.valueOf(i.substring(1)))
                                                   .findFirst();
            if (length.isPresent()) {
                return length.get() > 0 ? length.get() : DEFAULT_LENGTH;
            } else {
                log.debug("Length not specified. Default length [{}] shall be used", DEFAULT_LENGTH);
                return DEFAULT_LENGTH;
            }
        } catch (final Exception e) {
            log.warn("Error occured while interpreting length : {}. Default length of {} shall be used", e.getMessage(), DEFAULT_LENGTH);
            return DEFAULT_LENGTH;
        }
    }

    /**
     * Interprets the {@link DataType} from the identifiers.
     *
     * @param query
     *            to identify the {@link DataType}
     * @return {@link DataType}
     * @throws TypeInterpretationException
     *             if no or invalid {@link DataType} is given
     */
    public static DataType getDataType(final String query) {
        Optional<DataType> dataType;
        try {
            dataType = Arrays.stream(StringUtils.split(query))
                             .filter(i -> i.charAt(0) == Identifiers.TYPE.getIdentifier())
                             .map(i -> DataType.of(i.charAt(1)))
                             .findFirst();
        } catch (final Exception e) {
            throw new TypeInterpretationException("Invalid Data Type Specified");
        }

        if (dataType.isPresent()) {
            return dataType.get();
        } else {
            throw new TypeInterpretationException("No Data Type Specified.");
        }
    }

    /**
     * Interprets the Locale from the query params
     *
     * @param query
     *            to identify Locale of {@link Type} backed by {@link DataType}
     * @return Locale if specified, else {@link StringUtils#EMPTY}
     */
    public static String getLocale(final String query) {
        final Optional<String> locale = Arrays.stream(StringUtils.split(query))
                                              .filter(i -> i.charAt(0) == Identifiers.LOCALE.getIdentifier())
                                              .map(i -> i.substring(1))
                                              .findFirst();
        if (locale.isPresent()) {
            return locale.get();
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * Interprets the Property Identifiers from the query params.
     *
     * @param query
     *            to identify {@link Properties} of {@link Type} backed by
     *            {@link DataType}
     * @return Set of unique property identifiers.
     * @throws PropertiesInterpretationException
     *             if invalid {@link Properties} are found.
     */
    public static Set<Object> getPropertyIdentifiers(final String query) {
        Set<Object> propertyIdentifiers = Collections.EMPTY_SET;
        try {
            propertyIdentifiers = Arrays.stream(StringUtils.split(query))
                                        .filter(i -> i.charAt(0) == Identifiers.PROPERTY.getIdentifier())
                                        .map(i -> i.substring(1))
                                        .collect(Collectors.toSet());
            return propertyIdentifiers;
        } catch (final Exception e) {
            throw new PropertiesInterpretationException(e.getMessage());
        }
    }

    /**
     * Abstract method to be called via {@link TypeInterpreter}. Each
     * {@link DataType} has its own implementation for
     * {@link AbstractTypeInterpreter} which will be used to populate
     * {@link QueryBuilder} from identifiers.
     *
     * @param queryBuilder
     *            To be populated
     * @param query
     *            from which the {@link QueryBuilder} will be populated.
     * @throws PropertiesInterpretationException
     *             if invalid property is passed for {@link DataType} interpreted by
     *             {@link #getDataType(String )}
     * @throws QueryInterpretationException
     *             if required attributes of {@link DataType} are not identified
     */
    @Override
    public abstract void doInterpret(QueryBuilder queryBuilder, String query);

}
