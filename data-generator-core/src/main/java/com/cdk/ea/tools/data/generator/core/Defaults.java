package com.cdk.ea.tools.data.generator.core;

/**
 * Container holding reasonable defaults that data generator shall use.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 *
 * @since 11-02-2017
 * @version 1.0
 */
public enum Defaults {
    ;

    public static final int DEFAULT_LENGTH = 8;

    public static final int DEFAULT_QUANTITY = 100;

    public static final StringProperties DEFAULT_STRING_PROP = StringProperties.ALPHA;

    public static final NumberProperties DEFAULT_NUMBER_PROP = NumberProperties.INTEGER;

    public static final ListProperties DEFAULT_LIST_PROP = ListProperties.CUSTOM;

    public static final String DEFAULT_DATA_COLLECTOR_NAME = "dummyDataCollector";

    public static final String DEFAULT_FAKER_LOCALE = "en";

}
