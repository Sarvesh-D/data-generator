package com.cdk.ea.data.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.data.common.Identifier;
import com.cdk.ea.data.query.AbstractTypeInterpretationStrategy;
import com.cdk.ea.data.query.ListTypeInterpretationStrategy;
import com.cdk.ea.data.query.NumberTypeInterpretationStrategy;
import com.cdk.ea.data.query.RegexTypeInterpretationStrategy;
import com.cdk.ea.data.query.StringTypeInterpretationStrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DataType implements Identifier<Character> {

    STRING('s', StringTypeInterpretationStrategy.class),
    NUMBER('n', NumberTypeInterpretationStrategy.class),
    LIST('l', ListTypeInterpretationStrategy.class),
    REGEX('r', RegexTypeInterpretationStrategy.class);

    @Getter private final Character identifier;
    
    @Getter private final Class<? extends AbstractTypeInterpretationStrategy> typeInterpretationStrategy;

    public static final Map<Character, DataType> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(DataType.values())
		.collect(Collectors.toMap(DataType::getIdentifier, Function.identity()));
    }

    public static DataType of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }
    
}
