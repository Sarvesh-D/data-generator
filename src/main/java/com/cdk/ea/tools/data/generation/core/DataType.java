package com.cdk.ea.tools.data.generation.core;

import java.util.Arrays;
import java.util.Collections;
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

/**
 * Container for the Types supported by data-generator for generating random
 * data. Each Type is identified <i>Uniquely</i> by its character identifier.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum DataType implements Identifier<Character>, CMDLineArgHelper {

    /**
     * String Type. The Characters in String generated will have Properties as
     * defined by {@link StringProperties}
     * 
     * @see StringProperties
     */
    STRING('s', StringTypeInterpretationStrategy.class, "generate random Strings"),

    /**
     * Number Type. The Digits in Number generated will have Properties as
     * defined by {@link NumberProperties}
     * 
     * @see NumberProperties
     */
    NUMBER('n', NumberTypeInterpretationStrategy.class, "generate random Numbers"),

    /**
     * List Type. The Strings generated will have Properties as defined by
     * {@link ListProperties}
     * 
     * @see ListProperties
     */
    LIST('l', ListTypeInterpretationStrategy.class, "generate random values from pre-defined list"),

    /**
     * Regex Type. The Strings generated will have Properties as defined by
     * {@link RegexProperties}
     * 
     * @see RegexProperties
     */
    REGEX('r', RegexTypeInterpretationStrategy.class, "generate random strings matching regex");

    private static final Map<Character, DataType> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(DataType.values())
		.collect(Collectors.toMap(DataType::getIdentifier, Function.identity()));
    }

    @Getter
    private final Character identifier;

    @Getter
    private final Class<? extends AbstractTypeInterpretationStrategy> typeInterpretationStrategy;

    @Getter
    private final String help;

    public static Map<Character, DataType> getEnumMap() {
	return Collections.unmodifiableMap(ENUM_MAP);
    }

    /**
     * Returns the {@link DataType} which is identified by the identifier
     * passed. If there is not dataType for the passed identifier
     * <code>Null</code> is returned
     * 
     * @param identifier
     *            for which dataType is required.
     * @return {@link DataType} for the identifier.
     */
    public static DataType of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}
