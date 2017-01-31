package com.cdk.ea.tools.data.generation.common;

@FunctionalInterface
public interface Identifier<T> {
    
    T getIdentifier();

}
