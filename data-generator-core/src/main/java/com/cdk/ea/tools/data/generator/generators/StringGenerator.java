package com.cdk.ea.tools.data.generator.generators;

import static org.junit.Assert.assertNotNull;

import java.util.StringJoiner;

import com.cdk.ea.tools.data.generator.common.StringUtils;
import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.types.StringType;

import lombok.RequiredArgsConstructor;

/**
 * Generator class for generating random Strings.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see StringType
 */
@RequiredArgsConstructor
public class StringGenerator implements Generator<String> {

    private final StringType stringType;

    /**
     * Factory method to instantiate {@link StringGenerator}
     * 
     * @param stringType
     *            from which the generator is to be instantiated.
     * @return {@link StringGenerator}
     */
    public static StringGenerator of(StringType stringType) {
	assertNotNull("String Type cannot be null", stringType);
	return new StringGenerator(stringType);
    }

    /**
     * {@inheritDoc}. This generator generates a random String.
     */
    @Override
    public String generate() {
	/*
	 * TODO Once the control gets inside the loop for last character to be
	 * appended on the buffer, the map function on the Stream will be called
	 * [StringProperties.values().length - 1] times unnecessarily. Fix this.
	 */
	StringBuilder baseString = new StringBuilder(stringType.getLength());
	StringJoiner finalString = new StringJoiner(Constants.EMPTY_STRING,
		org.apache.commons.lang3.StringUtils.trimToEmpty(stringType.getPrefix()),
		org.apache.commons.lang3.StringUtils.trimToEmpty(stringType.getSuffix()));
	while (StringUtils.canAppend(baseString)) {
	    stringType.getProperties().stream()
		    .forEach(property -> StringUtils.append(baseString, property.getGenerator().generate()));
	}
	finalString.add(baseString);
	return finalString.toString();
    }

}
