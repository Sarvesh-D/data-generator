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
 * Container for the Properties supported by {@link DataType#REGEX}.
 * Each Regex Property is identified <i>Uniquely</i> by its character identifier.
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum RegexProperties implements Identifier<Character>, TypeProperties {

    /**
     * Identifier for custom/user-defined regex
     */
    EXPR(Properties.REGEX_EXPR.getIdentifier());

    @Getter
    private final Character identifier;

    public static final Map<Character, RegexProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(RegexProperties.values())
		.collect(Collectors.toMap(RegexProperties::getIdentifier, Function.identity()));
    }

    public static RegexProperties of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}
