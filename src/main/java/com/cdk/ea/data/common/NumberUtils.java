package com.cdk.ea.data.common;

import org.apache.commons.lang3.RandomStringUtils;

public enum NumberUtils {;
    
    private static char[] naturals = {'1','2','3','4','5','6','7','8','9'};
    
    public static int randomInteger() {
	return Integer.valueOf(RandomStringUtils.randomNumeric(1));
    }
    
    public static int randomNatural() {
	return Integer.valueOf(RandomStringUtils.random(1, naturals));
    }

}
