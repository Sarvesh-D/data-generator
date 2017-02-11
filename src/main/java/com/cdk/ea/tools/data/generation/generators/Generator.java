package com.cdk.ea.tools.data.generation.generators;

/**
 * Interface to be implemented by any implementation that can be used to
 * generate data of type T.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
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
