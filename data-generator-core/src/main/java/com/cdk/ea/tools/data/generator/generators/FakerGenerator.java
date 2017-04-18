package com.cdk.ea.tools.data.generator.generators;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;

import com.cdk.ea.tools.data.generator.types.FakerType;
import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;

/**
 * Generator class for generating sensible data via Faker library.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 18-04-2017
 * @version 1.5
 * @see FakerType
 */
@RequiredArgsConstructor
public class FakerGenerator implements Generator<String> {

    private final Faker faker;
    private final FakerType fakerType;

    /**
     * Factory method to instantiate {@link FakerGenerator}
     * 
     * @param fakerType
     *            from which the generator is to be instantiated.
     * @return {@link FakerGenerator}
     */
    public static FakerGenerator of(FakerType fakerType) {
	assertNotNull("String Type cannot be null", fakerType);
	return new FakerGenerator(new Faker(new Locale(fakerType.getLocale())), fakerType);
    }

    /**
     * {@inheritDoc}. This generator generates a sensible String.
     */
    @Override
    public String generate() {
	return faker.resolve(fakerType.getFakerProperties().getFakerKey());
    }

}
