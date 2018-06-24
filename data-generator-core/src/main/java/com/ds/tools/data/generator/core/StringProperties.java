package com.ds.tools.data.generator.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ds.tools.data.generator.common.CharacterUtils;
import com.ds.tools.data.generator.common.Identifier;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container for the Properties supported by {@link DataType#STRING}. Each
 * String Property is identified <i>Uniquely</i> by its character identifier.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
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

    private static final Map<Character, StringProperties> ENUM_MAP;

    static {
        ENUM_MAP = Arrays.stream(StringProperties.values())
                         .collect(Collectors.toMap(StringProperties::getIdentifier, Function.identity()));
    }

    @Getter
    private final Character identifier;

    @Getter
    private final Generator<Character> generator;

    public static Map<Character, StringProperties> getEnumMap() {
        return Collections.unmodifiableMap(ENUM_MAP);
    }

    public static StringProperties of(final char identifier) {
        return ENUM_MAP.get(identifier);
    }

}
