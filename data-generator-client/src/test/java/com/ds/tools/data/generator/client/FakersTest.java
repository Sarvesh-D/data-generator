/**
 *
 */
package com.ds.tools.data.generator.client;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class FakersTest {

    @Test
    public void test10FakerStringsToBeGenerated() {
        final List<String> strings = new Fakers().fakerKey("ancient.god")
                                                 .locale("in-ID")
                                                 .quantity(10)
                                                 .generate()
                                                 .collect(Collectors.toList());
        assertTrue("Exactly 10 faker strings should be generated", strings.size() == 10);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenNoFakerKeyIsProvided() {
        new Fakers().generate();
    }

}
