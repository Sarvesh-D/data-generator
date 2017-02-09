package com.cdk.ea.tools.data.generation.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.tools.data.generation.common.CharacterUtils;
import com.cdk.ea.tools.data.generation.common.Identifier;
import com.cdk.ea.tools.data.generation.generators.Generator;
import com.cdk.ea.tools.data.generation.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container for the Properties supported by {@link DataType#STRING}.
 * Each String Property is identified <i>Uniquely</i> by its character identifier.
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum StringProperties implements Identifier<Character>, TypeProperties {

    /**
     * Identifier for String containing only alpha characters
     */
    ALPHA(Properties.ALPHA_STRING.getIdentifier(), CharacterUtils::randomAlphaCharacter),
    
    /**
     * Identifier for String containing only numeric characters
     */
    NUMERIC(Properties.NUMERIC_STRING.getIdentifier(), CharacterUtils::randomNumericCharacter),
    
    /**
     * Identifier for String containing only special characters
     */
    SPECIAL_CHARS(Properties.SPECIAL_STRING.getIdentifier(), CharacterUtils::randomSpecialCharacter);

    @Getter
    private final Character identifier;

    @Getter
    private final Generator<Character> generator;

    public static final Map<Character, StringProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(StringProperties.values())
		.collect(Collectors.toMap(StringProperties::getIdentifier, Function.identity()));
    }

    public static StringProperties of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}
