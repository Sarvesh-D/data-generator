package com.cdk.ea.data.generators;

@FunctionalInterface
public interface Generator<T> {
    
    T generate();

}
