package com.cdk.ea.data.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.data.common.CharacterUtils;
import com.cdk.ea.data.common.Identifier;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StringProperties implements Identifier<Character>, TypeProperties {
    
    ALPHA(Properties.ALPHA_STRING.getIdentifier(), CharacterUtils::randomAlphaCharacter),
    NUMERIC(Properties.NUMERIC_STRING.getIdentifier(), CharacterUtils::randomNumericCharacter),
    SPECIAL_CHARS(Properties.SPECIAL_STRING.getIdentifier(), CharacterUtils::randomSpecialCharacter);
    
    @Getter private final Character identifier;
 
    @Getter private final Generator<Character> generator;
    
    public static final Map<Character, StringProperties> ENUM_MAP;
    
    static {
	ENUM_MAP = Arrays.stream(StringProperties.values())
			.collect(Collectors.toMap(StringProperties::getIdentifier, Function.identity()));
    }
    
    public static StringProperties of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }
    
}
