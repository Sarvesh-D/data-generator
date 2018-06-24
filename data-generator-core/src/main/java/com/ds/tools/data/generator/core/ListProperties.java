package com.ds.tools.data.generator.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ds.tools.data.generator.common.Identifier;
import com.ds.tools.data.generator.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container for the Properties supported by {@link DataType#LIST}. Each List
 * Property is identified <i>Uniquely</i> by its character identifier.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum ListProperties implements Identifier<Character>, TypeProperties {

    /**
     * Identifier for generating random data from user-defined list
     */
    CUSTOM(Properties.CUSTOM_LIST.getIdentifier());

    private static final Map<Character, ListProperties> ENUM_MAP;

    static {
        ENUM_MAP = Arrays.stream(ListProperties.values())
                         .collect(Collectors.toMap(ListProperties::getIdentifier, Function.identity()));
    }

    @Getter
    private final Character identifier;

    public static Map<Character, ListProperties> getEnumMap() {
        return Collections.unmodifiableMap(ENUM_MAP);
    }

    public static ListProperties of(final char identifier) {
        return ENUM_MAP.get(identifier);
    }

}
