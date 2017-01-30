package com.cdk.ea.data.generators;

import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.NumberProperties;
import com.cdk.ea.data.types.NumberType.NumberTypeBuilder;

@RunWith(JUnit4.class)
public class NumberGeneratorTest {
    
    private NumberTypeBuilder numberTypeBuilder;

    @Before
    public void setUp() throws Exception {
	numberTypeBuilder = (NumberTypeBuilder) new NumberTypeBuilder().setDataType(DataType.NUMBER).setLength(10);
    }

    @After
    public void tearDown() throws Exception {
	numberTypeBuilder = null;
    }

    @Test
    public final void testGeneratedNumbers() {
	numberTypeBuilder.setTypeProperties(EnumSet.of(NumberProperties.INTEGER));
	IntStream.range(0, 50).forEach(i -> 
	    assertTrue("Not a valid number", StringUtils.isNumeric(generate().toString()))
	);
    }
    
    @Test
    public final void testGeneratedNumberLength() {
	final int length = 13;
	numberTypeBuilder.setLength(length).setTypeProperties(EnumSet.of(NumberProperties.INTEGER));
	IntStream.range(0, 50).forEach(i -> {
	    String generatedNumber = generate().toString();
	    assertTrue("Number must be of length : ["+length+"]", StringUtils.isNumeric(generatedNumber) && generatedNumber.length() == length);
	});
    }
    
    private Number generate() {
	return numberTypeBuilder.buildType().generator().generate();
    }

}
