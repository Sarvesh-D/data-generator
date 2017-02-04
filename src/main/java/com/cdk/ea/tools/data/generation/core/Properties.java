package com.cdk.ea.tools.data.generation.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.tools.data.generation.common.CMDLineArgHelper;
import com.cdk.ea.tools.data.generation.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Properties implements Identifier<Character>, CMDLineArgHelper {

    ALPHA_STRING('a', "alpha string"),
    NUMERIC_STRING('n', "numeric string"),
    SPECIAL_STRING('s', "special character string"),
    INTEGER_NUMBER('i', "long number"),
    CUSTOM_LIST('u', "custom list"),
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