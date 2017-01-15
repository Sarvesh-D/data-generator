package com.cdk.ea.data.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.data.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DataType implements Identifier<Character> {

    STRING('s'),
    NUMBER('n');

    @Getter private final Character identifier;

    public static final Map<Character, DataType> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(DataType.values())
		.collect(Collectors.toMap(DataType::getIdentifier, Function.identity()));
    }

    public static DataType of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }
    
}
