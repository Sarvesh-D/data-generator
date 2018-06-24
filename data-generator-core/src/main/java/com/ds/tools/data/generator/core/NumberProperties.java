package com.ds.tools.data.generator.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ds.tools.data.generator.common.Identifier;
import com.ds.tools.data.generator.common.NumberUtils;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container for the Properties supported by {@link DataType#NUMBER}. Each
 * Number Property is identified <i>Uniquely</i> by its character identifier.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum NumberProperties implements Identifier<Character>, TypeProperties {

    /**
     * Identifier for generating integer numbers
     */
    INTEGER(Properties.INTEGER_NUMBER.getIdentifier(), NumberUtils::randomInteger);

    private static final Map<Character, NumberProperties> ENUM_MAP;

    static {
        ENUM_MAP = Arrays.stream(NumberProperties.values())
                         .collect(Collectors.toMap(NumberProperties::getIdentifier, Function.identity()));
    }

    @Getter
    private final Character identifier;

    @Getter
    private final Generator<Integer> generator;

    public static Map<Character, NumberProperties> getEnumMap() {
        return Collections.unmodifiableMap(ENUM_MAP);
    }

    public static NumberProperties of(final char identifier) {
        return ENUM_MAP.get(identifier);
    }

}
