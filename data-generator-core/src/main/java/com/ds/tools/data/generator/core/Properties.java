package com.ds.tools.data.generator.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ds.tools.data.generator.common.CMDLineArgHelper;
import com.ds.tools.data.generator.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container to hold various properties supported by different DataTypes. This
 * container provides a uniform way to deal with different DataTypes.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum Properties implements Identifier<Character>, CMDLineArgHelper {

    /**
     * Identifier for String containing only alpha characters
     */
    ALPHA_STRING('a', "alpha string"),

    /**
     * Identifier for String containing only numeric characters
     */
    NUMERIC_STRING('n', "numeric string"),

    /**
     * Identifier for String containing only special characters
     */
    SPECIAL_STRING('s', "special character string"),

    /**
     * Identifier for Integer numbers
     */
    INTEGER_NUMBER('i', "long number"),

    /**
     * Identifier for custom/user-defined list
     */
    CUSTOM_LIST('u', "custom list"),

    /**
     * Identifier for custom/user-defined regex
     */
    REGEX_EXPR('r', "custom regex");

    private static final Map<Character, Properties> ENUM_MAP;

    static {
        ENUM_MAP = Arrays.stream(Properties.values())
                         .collect(Collectors.toMap(Properties::getIdentifier, Function.identity()));
    }

    @Getter
    private final Character identifier;

    @Getter
    private final String help;

    public static Map<Character, Properties> getEnumMap() {
        return Collections.unmodifiableMap(ENUM_MAP);
    }

    public static Properties of(final char identifier) {
        return ENUM_MAP.get(identifier);
    }

}
