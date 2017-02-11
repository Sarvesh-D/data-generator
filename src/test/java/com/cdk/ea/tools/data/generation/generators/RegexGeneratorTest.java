package com.cdk.ea.tools.data.generation.generators;

import static org.junit.Assert.assertTrue;

import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generation.types.RegexType.RegexTypeBuilder;

/**
 * Test Class for testing {@link RegexGenerator}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class RegexGeneratorTest {

    private RegexTypeBuilder regexTypeBuilder;

    @Before
    public void setUp() throws Exception {
	regexTypeBuilder = (RegexTypeBuilder) new RegexTypeBuilder().setLength(10);
    }

    @After
    public void tearDown() throws Exception {
	regexTypeBuilder = null;
    }

    @Test
    public final void testGeneratedStrings() {
	final String regex = "[a-zA-Z0-9]+[@]";
	regexTypeBuilder.setRegex(regex);
	IntStream.range(1, 50)
		.forEach(i -> assertTrue("Generated string must match regex -> " + regex, generate().matches(regex)));
    }

    private String generate() {
	return regexTypeBuilder.buildType().generator().generate();
    }

}
