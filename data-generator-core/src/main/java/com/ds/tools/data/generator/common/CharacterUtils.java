package com.ds.tools.data.generator.common;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import com.ds.tools.data.generator.core.SpecialChar;

/**
 * Utility class for generation different <code>char</code>.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
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
        return RandomStringUtils.randomAlphabetic(1)
                                .charAt(0);
    }

    /**
     * Generates a random single <code>char</code> which is guaranteed to be an
     * Number(0-9).
     *
     * @return a number <code>char</code> between 0-9.
     */
    public static char randomNumericCharacter() {
        return RandomStringUtils.randomNumeric(1)
                                .charAt(0);
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
        return SpecialChar.values()[RandomUtils.nextInt(0, SpecialChar.values().length)].getIdentifier();
    }

}
