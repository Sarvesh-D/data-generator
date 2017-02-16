package com.cdk.ea.tools.data.generation.common;

/**
 * Builder interface to be implemented by types using Builder Pattern. for
 * building the type.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @param <T>
 *            Type of the object to be built.
 * @since 07-02-2017
 * @version 1.0
 */
public interface Builder<T> {

    /**
     * Build instance of Type <b>T</b>. Implementations may return singleton or
     * return prototype.
     * 
     * @param typeParams
     *            additional parameters required to build the instance of type
     *            <b>T</b>. Can be null.
     * @return An instance of type <b>T</b>
     */
    T build(String... typeParams);

}
