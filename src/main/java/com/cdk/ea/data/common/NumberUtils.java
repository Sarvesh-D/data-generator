package com.cdk.ea.data.common;

import org.apache.commons.lang.RandomStringUtils;

public class NumberUtils {
    
    private NumberUtils() {
	// suppressing default constructor
    }
    
    public static int randomInteger() {
	return Integer.valueOf(RandomStringUtils.randomNumeric(1));
    }
    

}
