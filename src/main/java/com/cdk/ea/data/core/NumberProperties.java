package com.cdk.ea.data.core;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.cdk.ea.data.common.Identifier;
import com.cdk.ea.data.common.NumberUtils;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.types.TypeProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum NumberProperties implements Identifier<Character>, TypeProperties {
    
    INTEGER(Properties.INTEGER_NUMBER.getIdentifier(), NumberUtils::randomInteger);
    //DECIMAL('d'),
    //PRECISSION('p');
    
    @Getter private final Character identifier;
    
    @Getter private final Generator<Integer> generator;
    
    public static final Map<Character, NumberProperties> ENUM_MAP;
    
    static {
	ENUM_MAP = Arrays.stream(NumberProperties.values())
			.collect(Collectors.toMap(NumberProperties::getIdentifier, Function.identity()));
    }
    
    public static NumberProperties of(char identifier) {
	return ENUM_MAP.get(identifier); 
    }

}
