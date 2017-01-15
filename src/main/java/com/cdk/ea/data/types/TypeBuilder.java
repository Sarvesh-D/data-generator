package com.cdk.ea.data.types;

import java.util.Set;

import com.cdk.ea.data.core.DataType;

public interface TypeBuilder<T,U> {
    
    TypeBuilder<T,U> setDataType(DataType dataType);
    
    TypeBuilder<T,U> setTypeProperties(Set<U> properties);
    
    TypeBuilder<T,U> setLength(int length);
    
    T buildType();
    
}
