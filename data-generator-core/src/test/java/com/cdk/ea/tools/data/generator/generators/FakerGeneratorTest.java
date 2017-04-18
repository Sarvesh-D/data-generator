package com.cdk.ea.tools.data.generator.generators;

import static org.junit.Assert.assertNotNull;

import java.util.Locale;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.github.javafaker.Faker;

/**
 * Test Class for testing the {@link FakerGenerator}
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 *
 * @since 18-04-2017
 * @version 1.5
 */
@RunWith(JUnit4.class)
public class FakerGeneratorTest {
    
    private static String[] fakerKeys = {"ancient.god", "pokemon.names", "name.first_name"};
    
    private static String[] fakerLocales = { "ca", "ca-CAT", "da-DK", "de", "de-AT", "de-CH", "en", "en-AU",
	    "en-au-ocker", "en-BORK", "en-CA", "en-GB", "en-IND", "en-NEP", "en-NG", "en-NZ", "en-PAK", "en-SG",
	    "en-UG", "en-US", "en-ZA", "es", "es-MX", "fa", "fi-FI", "fr", "he", "in-ID", "it", "ja", "ko", "nb-NO",
	    "nl", "pl", "pt", "pt-BR", "ru", "sk", "sv", "sv-SE", "tr", "uk", "vi", "zh-CN", "zh-TW" };
    
    @Test
    public final void testFakerGenerator() {
	IntStream.range(0, 100).forEach(i -> doTest());
    }
    
    private static void doTest() {
	Faker faker = new Faker();
	String data = faker.resolve(getFakerKey());
	assertNotNull("Faker returned null data", data);
    }
    
    private static String getFakerKey() {
	return fakerKeys[RandomUtils.nextInt(0, fakerKeys.length)];
    }
    
    private static String getFakerLocale() {
	return fakerLocales[RandomUtils.nextInt(0, fakerLocales.length)];
    }

}
