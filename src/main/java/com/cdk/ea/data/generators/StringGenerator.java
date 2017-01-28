package com.cdk.ea.data.generators;

import static org.junit.Assert.assertNotNull;

import java.util.StringJoiner;

import com.cdk.ea.data.common.StringUtils;
import com.cdk.ea.data.core.Constants;
import com.cdk.ea.data.types.StringType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StringGenerator implements Generator<String> {
    
    private final StringType stringType;
    
    @Override
    public String generate() {
	/* TODO
	 * Once the control gets inside the loop for last character to be appended on the buffer,
	 * the map function on the Stream will be called [StringProperties.values().length - 1] times
	 * unnecessarily. Fix this.
	 */
	StringBuilder baseString = new StringBuilder(stringType.getLength());
	StringJoiner finalString = new StringJoiner(Constants.EMPTY_STRING,
		org.apache.commons.lang3.StringUtils.trimToEmpty(stringType.getPrefix()),
		org.apache.commons.lang3.StringUtils.trimToEmpty(stringType.getSuffix()));
	while(StringUtils.canAppend(baseString)) {
	    stringType.getProperties()
                	    .stream()
                	    .forEach(property -> StringUtils.append(baseString, property.getGenerator().generate()));
	}
	finalString.add(baseString);
	return finalString.toString();
    }
    
    public static StringGenerator of(StringType stringType) {
	assertNotNull("String Type cannot be null", stringType);
	return new StringGenerator(stringType);
    }
    
}
