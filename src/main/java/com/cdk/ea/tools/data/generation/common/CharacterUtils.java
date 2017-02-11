package com.cdk.ea.tools.data.generation.common;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import com.cdk.ea.tools.data.generation.core.SpecialChar;

/**
 * Utility class for generation different <code>char</code>.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
public enum CharacterUtils {
    ;

    /**
     * Generates a random single <code>char</code> which is guaranteed to be an
     * Alpha.
     * 
     * @return an alpha <code>char</code>
     */
    public static char randomAlphaCharacter() {
	return RandomStringUtils.randomAlphabetic(1).charAt(0);
    }

    /**
     * Generates a random single <code>char</code> which is guaranteed to be an
     * Number(0-9).
     * 
     * @return a number <code>char</code> between 0-9.
     */
    public static char randomNumericCharacter() {
	return RandomStringUtils.randomNumeric(1).charAt(0);
    }

    /**
     * Generates a random single <code>char</code> which is guaranteed to be a
     * special character. The generated special char will be one of
     * {@link SpecialChar}
     * 
     * @return a special character
     * @see SpecialChar
     */
    public static char randomSpecialCharacter() {
	return SpecialChar.values()[RandomUtils.nextInt(SpecialChar.values().length)].getIdentifier();
    }

}
