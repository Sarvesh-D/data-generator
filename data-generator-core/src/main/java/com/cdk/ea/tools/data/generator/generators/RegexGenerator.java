package com.cdk.ea.tools.data.generator.generators;

import static org.junit.Assert.assertNotNull;

import com.cdk.ea.tools.data.generator.types.RegexType;
import com.mifmif.common.regex.Generex;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Generator class for generating random Strings that match a given regex.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see RegexType
 */
@RequiredArgsConstructor
public class RegexGenerator implements Generator<String> {

    @NonNull
    private final Generex regex;

    // TODO accept length for genrated regex string
    /**
     * Factory method to instantiate {@link RegexGenerator}
     * 
     * @param regexType
     *            from which the generator is to be instantiated.
     * @return {@link RegexGenerator}
     */
    public static RegexGenerator of(RegexType regexType) {
	assertNotNull("Regex Type cannot be null", regexType);
	return new RegexGenerator(regexType.getRegex());
    }

    /**
     * {@inheritDoc}. This generator generates a random String matching a regex.
     */
    @Override
    public String generate() {
	return regex.random();
    }

}
