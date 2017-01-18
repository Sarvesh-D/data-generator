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
public enum ListProperties implements Identifier<Character>, TypeProperties {

    CUSTOM(Properties.CUSTOM_LIST.getIdentifier());

    @Getter private final Character identifier;

    public static final Map<Character, ListProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(ListProperties.values())
		.collect(Collectors.toMap(ListProperties::getIdentifier, Function.identity()));
    }

    public static ListProperties of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }

}
