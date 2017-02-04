package com.cdk.ea.tools.data.generation.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.tools.data.generation.common.CMDLineArgHelper;
import com.cdk.ea.tools.data.generation.common.Identifier;
import com.cdk.ea.tools.data.generation.query.interpreter.AbstractTypeInterpretationStrategy;
import com.cdk.ea.tools.data.generation.query.interpreter.ListTypeInterpretationStrategy;
import com.cdk.ea.tools.data.generation.query.interpreter.NumberTypeInterpretationStrategy;
import com.cdk.ea.tools.data.generation.query.interpreter.RegexTypeInterpretationStrategy;
import com.cdk.ea.tools.data.generation.query.interpreter.StringTypeInterpretationStrategy;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DataType implements Identifier<Character>, CMDLineArgHelper {

    STRING('s', StringTypeInterpretationStrategy.class, "generate random Strings"),
    NUMBER('n', NumberTypeInterpretationStrategy.class, "generate random Numbers"),
    LIST('l', ListTypeInterpretationStrategy.class, "generate random values from pre-defined list"),
    REGEX('r', RegexTypeInterpretationStrategy.class, "generate random strings matching regex");

    @Getter
    private final Character identifier;

    @Getter
    private final Class<? extends AbstractTypeInterpretationStrategy> typeInterpretationStrategy;

    @Getter
    private final String help;

    public static final Map<Character, DataType> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(DataType.values())
		.collect(Collectors.toMap(DataType::getIdentifier, Function.identity()));
    }

    public static DataType of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}
