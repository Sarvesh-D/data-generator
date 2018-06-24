package com.ds.tools.data.generator.common;

/**
 * Builder interface to be implemented by types using Builder Pattern. for
 * building the type.
 *
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @param <T>
 *            Type of the object to be built.
 * @param <U>
 *            helper object to build T
 * @since 07-02-2017
 * @version 1.0
 */
public interface Builder<T, U extends Object> {

    /**
     * Build instance of Type <b>T</b>. Implementations may return singleton or
     * return prototype.
     *
     * @param typeParam
     *            additional parameters required to build the instance of type
     *            <b>T</b>. Can be null.
     * @return An instance of type <b>T</b>
     */
    T build(U typeParam);

}
