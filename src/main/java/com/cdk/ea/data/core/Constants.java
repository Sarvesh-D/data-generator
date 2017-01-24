package com.cdk.ea.data.core;

public class Constants {

    public static final String GLOBAL_OVERRIDE = ""+Identifiers.PROPERTY.getIdentifier()
    + Identifiers.PROPERTY.getIdentifier() + Identifiers.OVERRIDE.getIdentifier();  // --o

    public static final String SPACE = " ";
    
    public static final String COMMA = ",";
    
    public static final String EMPTY_STRING = "";

    public static final String CUSTOM_LIST_VALS_PREFIX = "[[";

    public static final String CUSTOM_LIST_VALS_SUFFIX = "]]";

    public static final String REGEX_EXPR_PREFIX = "{{";

    public static final String REGEX_EXPR_SUFFIX = "}}";

    private Constants() {
	// suppressing default constructor
    }

}
