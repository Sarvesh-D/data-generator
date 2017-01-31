package com.cdk.ea.tools.data.generation.common;

public enum StringUtils {;
    
    public static String randomSpecialCharString(int length) {
	StringBuilder specialChars = new  StringBuilder(length);
	while(canAppend(specialChars)) {
	    specialChars.append(CharacterUtils.randomSpecialCharacter());
	}
	return specialChars.toString();
    }
    
    public static boolean append(StringBuilder target, Object c) {
	if(canAppend(target)) {
	    target.append(c);
	    return true;
	}
	return false;
    }
    
    public static boolean canAppend(StringBuilder target) {
	return target.length() < target.capacity();
    }
    

}
