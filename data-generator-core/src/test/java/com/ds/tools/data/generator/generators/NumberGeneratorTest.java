package com.ds.tools.data.generator.generators;

import static org.junit.Assert.assertTrue;

import java.util.EnumSet;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.NumberProperties;
import com.ds.tools.data.generator.types.NumberType.NumberTypeBuilder;

/**
 * Test Class for testing {@link NumberGenerator}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class NumberGeneratorTest {

    private NumberTypeBuilder numberTypeBuilder;

    @Before
    public void setUp() throws Exception {
        numberTypeBuilder = (NumberTypeBuilder) new NumberTypeBuilder().setDataType(DataType.NUMBER)
                                                                       .setLength(10);
    }

    @After
    public void tearDown() throws Exception {
        numberTypeBuilder = null;
    }

    @Test
    public final void testGeneratedNumberLength() {
        final int length = 13;
        numberTypeBuilder.setLength(length)
                         .setTypeProperties(EnumSet.of(NumberProperties.INTEGER));
        final NumberGenerator numberGenerator = getNumberGenerator();
        IntStream.range(0, 50)
                 .forEach(i -> {
                     final String generatedNumber = numberGenerator.generate()
                                                                   .toString();
                     assertTrue("Number must be of length : [" + length + "]", StringUtils.isNumeric(generatedNumber) && generatedNumber.length() == length);
                 });
    }

    @Test
    public final void testGeneratedNumbers() {
        numberTypeBuilder.setTypeProperties(EnumSet.of(NumberProperties.INTEGER));
        final NumberGenerator numberGenerator = getNumberGenerator();
        IntStream.range(0, 50)
                 .forEach(i -> assertTrue("Not a valid number", StringUtils.isNumeric(numberGenerator.generate()
                                                                                                     .toString())));
    }

    private NumberGenerator getNumberGenerator() {
        return NumberGenerator.of(numberTypeBuilder.buildType());
    }

}
