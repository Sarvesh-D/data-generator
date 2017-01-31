package com.cdk.ea.tools.data.generation.generators;

import static org.junit.Assert.assertNotNull;

import com.cdk.ea.tools.data.generation.types.RegexType;
import com.mifmif.common.regex.Generex;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegexGenerator implements Generator<String> {

    @NonNull
    private final Generex regex;
    
    @Override
    public String generate() {
	return regex.random();
    }

    // TODO accept length for genrated regex string
    public static RegexGenerator from(RegexType regexType) {
	assertNotNull("Regex Type cannot be null", regexType);
	return new RegexGenerator(regexType.getRegex());
    }

}
