package com.cdk.ea.data.common;

@FunctionalInterface
public interface Identifier<T> {
    
    T getIdentifier();

}
