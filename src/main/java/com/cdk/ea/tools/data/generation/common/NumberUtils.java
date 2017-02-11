package com.cdk.ea.tools.data.generation.common;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Utility class for generating different <code>int</code>.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
public enum NumberUtils {
    ;

    private static char[] naturals = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    /**
     * Generates a single digit random <code>int</code>.
     * 
     * @return a single digit random <code>int</code>.
     */
    public static int randomInteger() {
	return Integer.valueOf(RandomStringUtils.randomNumeric(1));
    }

    /**
     * Generates a single digit random natural <code>int</code>.
     * 
     * @return a single digit random natural <code>int</code>.
     */
    public static int randomNatural() {
	return Integer.valueOf(RandomStringUtils.random(1, naturals));
    }

}
