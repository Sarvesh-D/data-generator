package com.cdk.ea.tools.data.generator.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.tools.data.generator.common.Identifier;
import com.cdk.ea.tools.data.generator.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container for the Properties supported by {@link DataType#REGEX}. Each Regex
 * Property is identified <i>Uniquely</i> by its character identifier.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 07-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum RegexProperties implements Identifier<Character>, TypeProperties {

    /**
     * Identifier for custom/user-defined regex
     */
    EXPR(Properties.REGEX_EXPR.getIdentifier());

    private static final Map<Character, RegexProperties> ENUM_MAP;

    static {
	ENUM_MAP = Arrays.stream(RegexProperties.values())
		.collect(Collectors.toMap(RegexProperties::getIdentifier, Function.identity()));
    }

    @Getter
    private final Character identifier;

    public static Map<Character, RegexProperties> getEnumMap() {
	return Collections.unmodifiableMap(ENUM_MAP);
    }

    public static RegexProperties of(char identifier) {
	return ENUM_MAP.get(identifier);
    }

}
