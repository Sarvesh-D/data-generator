/**
 *
 */
package com.ds.tools.data.generator.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.ListProperties;
import com.ds.tools.data.generator.core.NumberProperties;
import com.ds.tools.data.generator.core.RegexProperties;
import com.ds.tools.data.generator.core.StringProperties;
import com.ds.tools.data.generator.types.TypeProperties;

/**
 * Utility class for various commonly used operations
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public final class Utils {

    private Utils() {
    }

    /**
     * Get {@link TypeProperties} for given {@link DataType}
     *
     * @param dataType
     * @return {@link TypeProperties}
     */
    public static Set<TypeProperties> getPropertiesFor(final DataType dataType) {
        switch (dataType) {
            case FAKER:
                return new HashSet<>();
            case LIST:
                return asSet(ListProperties.values());
            case NUMBER:
                return asSet(NumberProperties.values());
            case REGEX:
                return asSet(RegexProperties.values());
            case STRING:
                return asSet(StringProperties.values());
            default:
                return new HashSet<>();
        }
    }

    /**
     * Converts an T array into {@link Set} of T elements
     *
     * @param array
     * @return {@link Set} of T
     */
    public static <T> Set<T> asSet(final T[] array) {
        return Arrays.stream(array)
                     .collect(Collectors.toSet());
    }

}
