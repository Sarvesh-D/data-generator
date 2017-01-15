package com.cdk.ea.data.common;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.cdk.ea.data.core.SpecialChar;

public class CharacterUtils {
    
    private CharacterUtils() {
	// suppressing default constructor
    }
    
    public static char randomAlphaCharacter() {
	return RandomStringUtils.randomAlphabetic(1).charAt(0);
    }
    
    public static char randomNumericCharacter() {
	return RandomStringUtils.randomNumeric(1).charAt(0);
    }
    
    public static char randomSpecialCharacter() {
	return SpecialChar.values()[RandomUtils.nextInt(SpecialChar.values().length)].getIdentifier();
    }

}
