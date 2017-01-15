package com.cdk.ea.data.generators;

import static org.junit.Assert.assertNotNull;

import com.cdk.ea.data.common.StringUtils;
import com.cdk.ea.data.types.StringType;

public class StringGenerator implements Generator<String> {
    
    private final StringType stringType;
    
    private static StringGenerator instance;
    
    private StringGenerator(StringType stringType) {
	this.stringType = stringType;
    }

    @Override
    public String generate() {
	/* TODO
	 * Once the control gets inside the loop for last character to be appended on the buffer,
	 * the map function on the Stream will be called [StringProperties.values().length - 1] times
	 * unnecessarily. Fix this.
	 */
	StringBuilder generatedString = new StringBuilder(stringType.getLength());
	while(StringUtils.canAppend(generatedString)) {
	    stringType.getProperties()
                	    .stream()
                	    .map(property -> StringUtils.append(generatedString, property.getGenerator().generate()))
                	    .count(); // TODO fix the way stream is terminating
	}
	return generatedString.toString();
    }
    
    public static StringGenerator of(StringType stringType) {
	assertNotNull("String Type cannot be null", stringType);
	instance = new StringGenerator(stringType);
	 return instance;
    }
    
}
