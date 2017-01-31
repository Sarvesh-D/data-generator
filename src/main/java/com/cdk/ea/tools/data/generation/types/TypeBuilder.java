package com.cdk.ea.tools.data.generation.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.tools.data.generation.core.DataType;

public interface TypeBuilder<T,U> {
    
    TypeBuilder<T,U> setDataType(DataType dataType);
    
    default TypeBuilder<T,U> setTypeProperties(Set<U> properties) {
	return this;
    }
    
    default TypeBuilder<T,U> setLength(int length) {
	return this;
    }
    
    default TypeBuilder<T,U> setData(Collection<Object> data) {
	return this;
    }
    
    T buildType();
    
}
