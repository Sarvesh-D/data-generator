package com.cdk.ea.data.generators;

import com.cdk.ea.data.types.RegexType;
import com.mifmif.common.regex.Generex;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class RegexGenerator implements Generator<String> {

    @NonNull
    private final Generex regex;
    
    @Override
    public String generate() {
	return regex.random();
    }
    
    public static RegexGenerator from(RegexType regexType) {
	return new RegexGenerator(regexType.getRegex());
    }

}
