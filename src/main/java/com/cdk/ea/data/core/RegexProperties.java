package com.cdk.ea.data.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.data.common.Identifier;
import com.cdk.ea.data.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RegexProperties implements Identifier<Character>, TypeProperties {
    
    EXPR(Properties.REGEX_EXPR.getIdentifier());
    
    @Getter private final Character identifier;

    public static final Map<Character, RegexProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(RegexProperties.values())
		.collect(Collectors.toMap(RegexProperties::getIdentifier, Function.identity()));
    }

    public static RegexProperties of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }

}
