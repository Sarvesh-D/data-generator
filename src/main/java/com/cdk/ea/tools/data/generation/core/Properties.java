package com.cdk.ea.tools.data.generation.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.tools.data.generation.common.CMDLineArgHelper;
import com.cdk.ea.tools.data.generation.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container to hold various properties supported by different DataTypes.
 * This container provides a uniform way to deal with
 * different DataTypes.
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
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

    @Getter
    private final Character identifier;

    @Getter
    private final String help;

    public static final Map<Character, Properties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(Properties.values())
		.collect(Collectors.toMap(Properties::getIdentifier, Function.identity()));
    }

    public static Properties of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}