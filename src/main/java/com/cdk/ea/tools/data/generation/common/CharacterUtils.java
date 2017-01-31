package com.cdk.ea.tools.data.generation.common;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.cdk.ea.tools.data.generation.core.SpecialChar;

public enum CharacterUtils {;
    
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
