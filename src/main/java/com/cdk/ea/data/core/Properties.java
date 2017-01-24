package com.cdk.ea.data.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.data.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Properties implements Identifier<Character> {

    ALPHA_STRING('a'),
    NUMERIC_STRING('n'),
    SPECIAL_STRING('s'),
    INTEGER_NUMBER('i'),
    CUSTOM_LIST('u'),
    REGEX_EXPR('r');

    @Getter private final Character identifier;

    public static final Map<Character, Properties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(Properties.values())
		.collect(Collectors.toMap(Properties::getIdentifier, Function.identity()));
    }

    public static Properties of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }
    
}