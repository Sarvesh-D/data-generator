package com.cdk.ea.data.common;

import org.apache.commons.lang.RandomStringUtils;

public enum NumberUtils {;
    
    public static int randomInteger() {
	return Integer.valueOf(RandomStringUtils.randomNumeric(1));
    }
    

}
