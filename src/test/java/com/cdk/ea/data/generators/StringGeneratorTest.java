package com.cdk.ea.data.generators;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.SpecialChar;
import com.cdk.ea.data.core.StringProperties;
import com.cdk.ea.data.types.StringType.StringTypeBuilder;

@RunWith(JUnit4.class)
public class StringGeneratorTest {
    
    private StringTypeBuilder stringTypeBuilder;
    private char[] validSpecialChars;

    @Before
    public void setUp() throws Exception {
	stringTypeBuilder = (StringTypeBuilder) new StringTypeBuilder().setDataType(DataType.STRING)
		.setTypeProperties(EnumSet.of(StringProperties.ALPHA)).setLength(10);
	Arrays.stream(SpecialChar.values())
		.map(specialChar -> specialChar.getIdentifier())
		.forEach(c -> validSpecialChars = ArrayUtils.add(validSpecialChars, c));
    }

    @After
    public void tearDown() throws Exception {
	stringTypeBuilder = null;
	validSpecialChars = null;
    }

    @Test
    public final void testAlphaStringGenerated() {
	stringTypeBuilder.setTypeProperties(EnumSet.of(StringProperties.ALPHA));
	IntStream.range(0, 50).forEach(i ->
	    assertTrue("Alpha String must contain only alpha characters", StringUtils.isAlpha(generate()))
	);
    }
    
    @Test
    public final void testNumericStringGenerated() {
	stringTypeBuilder.setTypeProperties(EnumSet.of(StringProperties.NUMERIC));
	IntStream.range(0, 50).forEach(i -> 
	    assertTrue("Numeric String must contain only number characters", StringUtils.isNumeric(generate()))
	);
    }
    
    @Test
    public final void testSpecicalCharStringGenerated() {
	stringTypeBuilder.setTypeProperties(EnumSet.of(StringProperties.SPECIAL_CHARS));
	IntStream.range(0, 50).forEach(i ->
	    assertTrue("Special Char String must contain only special characters", StringUtils.containsOnly(generate(), validSpecialChars))
	);
    }
    
    @Test
    public final void testAlphaNumericSpecialCharStringGenerated() {
	stringTypeBuilder.setTypeProperties(EnumSet.of(StringProperties.ALPHA, StringProperties.NUMERIC, StringProperties.SPECIAL_CHARS));
	IntStream.range(0, 50).forEach(i -> {
	    
	    
	});
	String generatedString = generate();
	Arrays.stream(ArrayUtils.toObject(generatedString.toCharArray()))
		.map(c -> c.toString())
		.forEach(c -> {
		    assertTrue("String must contain alphabet, number and valid special characters",
			    StringUtils.isAlpha(c) || StringUtils.isNumeric(c)
				    || StringUtils.containsAny(c, validSpecialChars));
		});
    }
    
    @Test
    public final void testStringPrefixGenerated() {
	final String prefix = "test";
	stringTypeBuilder.setPrefix(prefix);
	IntStream.range(0, 50).forEach(i -> assertTrue("String must have prefix ["+prefix+"]", generate().startsWith(prefix)));
    }
    	
    @Test
    public final void testStringSuffixGenerated() {
	final String suffix = "bye";
	stringTypeBuilder.setSuffix(suffix);
	IntStream.range(0, 50).forEach(i -> assertTrue("String must have suffix ["+suffix+"]", generate().endsWith(suffix)));
    }
    
    @Test
    public final void testStringPrefixSuffixGenerated() {
	final String prefix = "test";
	final String suffix = "bye";
	stringTypeBuilder.setPrefix(prefix);
	stringTypeBuilder.setSuffix(suffix);
	IntStream.range(0, 50).forEach(i -> assertTrue("String must have prefix ["+prefix+"] and suffix ["+suffix+"]", generate().endsWith(suffix)));
    }
    
    @Test
    public final void testStringLengthsGenerated() {
	final int length = 15;
	stringTypeBuilder.setLength(length);
	IntStream.range(0, 50).forEach(i -> assertTrue("String must be of length ["+length+"].", generate().length() == length));
    }
    
    public final void testRandomSpecialCharString() {
	IntStream.range(0, 50).forEach(i -> {
	    assertTrue("Five special char must be genrated", com.cdk.ea.data.common.StringUtils.randomSpecialCharString(5).length() == 5);
	    assertTrue("All special chars must be from SpecialChars enum", StringUtils.containsOnly(com.cdk.ea.data.common.StringUtils.randomSpecialCharString(5), validSpecialChars));
	});
    }
    
    private final String generate() {
	return stringTypeBuilder.buildType().generator().generate();
    }

}