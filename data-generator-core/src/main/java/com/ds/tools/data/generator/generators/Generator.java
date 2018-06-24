package com.ds.tools.data.generator.generators;

/**
 * Interface to be implemented by any implementation that can be used to
 * generate data of type T.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @param <T>
 *            Type of data this generator will generate.
 * @since 10-02-2017
 * @version 1.0
 * @see DataGenerator
 * @see ListGenerator
 * @see NumberGenerator
 * @see RegexGenerator
 * @see StringGenerator
 */
@FunctionalInterface
public interface Generator<T> {

    /**
     * Generates data
     *
     * @return single record of type T
     */
    T generate();

}
