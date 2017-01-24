package com.cdk.ea.data.generators;

import static org.junit.Assert.assertNotNull;

import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.data.types.PatternStringType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PatternStringGenerator implements Generator<String> {
    
    private final PatternStringType patternStringType;
    
    @Override
    public String generate() {
	String baseString = patternStringType.getStringType().generator().generate();
	StringJoiner stringJoiner = new StringJoiner("", StringUtils.trimToEmpty(patternStringType.getPrefix()),
		StringUtils.trimToEmpty(patternStringType.getSuffix()));
	stringJoiner.add(baseString);
	return stringJoiner.toString();
    }
    
    public static PatternStringGenerator of(PatternStringType patternStringType) {
	assertNotNull("Pattern String Type cannot be null", patternStringType);
	assertNotNull("String Type cannot be null", patternStringType.getStringType());
	return new PatternStringGenerator(patternStringType);
    }

}
