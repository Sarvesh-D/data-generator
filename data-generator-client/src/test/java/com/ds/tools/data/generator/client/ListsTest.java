/**
 *
 */
package com.ds.tools.data.generator.client;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
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
public class ListsTest {

    @Test
    public void test10ElementsGeneratedFromCustomData() {
        final List<Object> data = Arrays.asList("Hello", "World", "foo", "bar");
        final List<Object> elements = new Lists().custom(data)
                                                 .quantity(10)
                                                 .generate()
                                                 .collect(Collectors.toList());

        assertTrue("Exactly 10 elements should be generated", elements.size() == 10);
        assertTrue("Each element generated must be from custom data supplied", elements.stream()
                                                                                       .filter(data::contains)
                                                                                       .count() == elements.size());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionWhenNoDataIsSupplied() {
        new Lists().generate();
    }

}
