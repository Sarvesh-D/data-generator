package com.ds.tools.data.generator.core;

/**
 * Enum for constants used throughout the data-generator code base.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 07-02-2017
 * @version 1.0
 */
public enum Constants {
    ;

    public static final String GLOBAL_OVERRIDE = Constants.EMPTY_STRING + Identifiers.PROPERTY.getIdentifier() + Identifiers.PROPERTY.getIdentifier() + Identifiers.OVERRIDE.getIdentifier(); // --o

    public static final String JSON_EXTENSTION = ".json";

    public static final String JSON = "json";

    public static final String SPACE = " ";

    public static final String COMMA = ",";

    public static final String EMPTY_STRING = "";

    public static final String CUSTOM_LIST_VALS_PREFIX = "[[";

    public static final String CUSTOM_LIST_VALS_SUFFIX = "]]";

    public static final String REGEX_EXPR_PREFIX = "{{";

    public static final String REGEX_EXPR_SUFFIX = "}}";

    public static final String DEBUG_ENABLED = "-X";

    public static final String DISPLAY_HELP = "--help";

    /**
     * Separator for separating multiple queries
     */
    public static final String CLI_QUERY_SEPARATOR = ";";

}
