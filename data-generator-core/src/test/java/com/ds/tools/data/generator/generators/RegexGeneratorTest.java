package com.ds.tools.data.generator.generators;

import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.types.RegexType.RegexTypeBuilder;

/**
 * Test Class for testing {@link RegexGenerator}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class RegexGeneratorTest {

    private RegexTypeBuilder regexTypeBuilder;

    @Before
    public void setUp() throws Exception {
        regexTypeBuilder = (RegexTypeBuilder) new RegexTypeBuilder().setDataType(DataType.REGEX)
                                                                    .setLength(10);
    }

    @After
    public void tearDown() throws Exception {
        regexTypeBuilder = null;
    }

    @Test
    public final void testGeneratedStrings() {
        final String regex = "[a-zA-Z0-9]+[@]";
        regexTypeBuilder.setRegex(regex);
        final RegexGenerator regexGenerator = getRegexGenerator();
        IntStream.range(1, 50)
                 .forEach(i -> assertTrue("Generated string must match regex -> " + regex, regexGenerator.generate()
                                                                                                         .matches(regex)));
    }

    private RegexGenerator getRegexGenerator() {
        return RegexGenerator.of(regexTypeBuilder.buildType());
    }

}
