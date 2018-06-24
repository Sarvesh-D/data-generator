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
public class RegexesTest {

    @Test
    public void test() {
        final String regex = "[a-zA-Z0-9]+[@]";
        final List<String> strings = new Regexes().pattern(regex)
                                                  .quantity(50)
                                                  .generate()
                                                  .collect(Collectors.toList());
        assertTrue("No Data generated", strings.size() > 0);
        assertTrue("All strings generated must have given regex pattern", strings.stream()
                                                                                 .filter(s -> s.matches(regex))
                                                                                 .count() == strings.size());

    }

}
