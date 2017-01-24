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
public enum PatternStringProperties implements Identifier<Character>, TypeProperties {
    
    PREFIX(Properties.STRING_PREFIX.getIdentifier()),
    SUFFIX(Properties.STRING_SUFFIX.getIdentifier());

    @Getter private final Character identifier;

    public static final Map<Character, PatternStringProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(PatternStringProperties.values())
		.collect(Collectors.toMap(PatternStringProperties::getIdentifier, Function.identity()));
    }

    public static PatternStringProperties of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }

}
