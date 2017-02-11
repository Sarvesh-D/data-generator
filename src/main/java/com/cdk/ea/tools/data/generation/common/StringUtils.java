package com.cdk.ea.tools.data.generation.common;

import com.cdk.ea.tools.data.generation.core.SpecialChar;

/**
 * Utility class for working with Strings.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 07-02-2017
 * @version 1.0
 */
public enum StringUtils {
    ;

    /**
     * Appends the String value of Object to the target. The method does not
     * check for the String length of object to be appended, and is assumed to
     * be 1. The String value of the passed object will be appended if
     * {@link StringUtils#canAppend(StringBuilder)} return <code>True</code>
     * 
     * @param target
     *            StringBuilder to which the object is to be appended
     * @param c
     *            object to be appended
     * @return <code>True if the append happened, <code>False<code> otherwise.
     */
    public static boolean append(StringBuilder target, Object c) {
	if (canAppend(target)) {
	    target.append(c);
	    return true;
	}
	return false;
    }

    /**
     * Checks if the target can contain one more character.
     * 
     * @param target
     *            StringBuilder to be checked
     * @return <code>True</code> if the target can contain one more
     *         <code>char</code>, <False> otherwise.
     */
    public static boolean canAppend(StringBuilder target) {
	return target.length() < target.capacity();
    }

    /**
     * Generates a String of length specified. The generated String is
     * guaranteed to contain only special chars as defined by
     * {@link SpecialChar}
     * 
     * @param length
     *            of the String to be generated
     * @return String containing special chars
     * @see SpecialChar
     */
    public static String randomSpecialCharString(int length) {
	StringBuilder specialChars = new StringBuilder(length);
	while (canAppend(specialChars)) {
	    specialChars.append(CharacterUtils.randomSpecialCharacter());
	}
	return specialChars.toString();
    }

}
