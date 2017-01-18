package com.cdk.ea.data.generators;

import static org.junit.Assert.assertNotNull;

import com.cdk.ea.data.common.StringUtils;
import com.cdk.ea.data.types.NumberType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NumberGenerator implements Generator<Number> {
    
    private final NumberType numberType;
    
    @Override
    public Number generate() {
	/* TODO
	 * Once the control gets inside the loop for last character to be appended on the buffer,
	 * the map function on the Stream will be called [NumberProperties.values().length - 1] times
	 * unnecessarily. Fix this.
	 */
	StringBuilder generatedNumber = new StringBuilder(numberType.getLength());
	while(StringUtils.canAppend(generatedNumber)) {
	    numberType.getProperties()
                	    .stream()
                	    .map(property -> StringUtils.append(generatedNumber, property.getGenerator().generate()))
                	    .count(); // TODO fix the way stream is terminating
	}
	return Integer.valueOf(generatedNumber.toString());
    }
    
    public static NumberGenerator of(NumberType numberType) {
	assertNotNull("Number Type cannot be null", numberType);
	 return new NumberGenerator(numberType);
    }

}
