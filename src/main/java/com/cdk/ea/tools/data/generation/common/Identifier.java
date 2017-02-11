package com.cdk.ea.tools.data.generation.common;

/**
 * Interface to be implemented by enums which serves as container for holding
 * the different arguments that can be passed as identifiers for generating data
 * via CLI or JSON.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @param <T>
 *            The type of the identifier.
 * @since 07-02-2017
 * @version 1.0
 */
@FunctionalInterface
public interface Identifier<T> {

    /**
     * Returns the identifier by which a particular CLI/JSON argument can be
     * identified.
     * 
     * @return identifier for particular CLI/JSON argument
     */
    T getIdentifier();

}
