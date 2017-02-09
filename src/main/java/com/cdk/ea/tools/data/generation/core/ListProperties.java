package com.cdk.ea.tools.data.generation.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.tools.data.generation.common.Identifier;
import com.cdk.ea.tools.data.generation.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container for the Properties supported by {@link DataType#LIST}.
 * Each List Property is identified <i>Uniquely</i> by its character identifier.
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum ListProperties implements Identifier<Character>, TypeProperties {

    /**
     * Identifier for generating random data from user-defined list
     */
    CUSTOM(Properties.CUSTOM_LIST.getIdentifier());

    @Getter
    private final Character identifier;

    public static final Map<Character, ListProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(ListProperties.values())
		.collect(Collectors.toMap(ListProperties::getIdentifier, Function.identity()));
    }

    public static ListProperties of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}
