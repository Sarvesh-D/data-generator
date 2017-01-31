package com.cdk.ea.tools.data.generation.generators;

@FunctionalInterface
public interface Generator<T> {
    
    T generate();

}
