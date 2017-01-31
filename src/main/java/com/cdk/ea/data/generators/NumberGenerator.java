package com.cdk.ea.data.generators;

import static org.junit.Assert.assertNotNull;

import com.cdk.ea.data.common.NumberUtils;
import com.cdk.ea.data.common.StringUtils;
import com.cdk.ea.data.types.NumberType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
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
	// add first number as natural to avoid 0 in the beginning. 0 in beginning causes length of generated number to be 1 less than desired
	// example: Long.valueOf("01") gives 1 instead of 01.
	generatedNumber.append(NumberUtils.randomNatural()); 
	while(StringUtils.canAppend(generatedNumber)) {
	    numberType.getProperties()
                	    .stream()
                	    .forEach(property -> StringUtils.append(generatedNumber, property.getGenerator().generate()));
	}
	return Long.valueOf(generatedNumber.toString());
    }
    
    public static NumberGenerator of(NumberType numberType) {
	assertNotNull("Number Type cannot be null", numberType);
	 return new NumberGenerator(numberType);
    }

}
