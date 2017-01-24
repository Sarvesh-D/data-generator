package com.cdk.ea.data.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.data.core.DataType;

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
    
    default TypeBuilder<T,U> setPrefix(String prefix) {
	return this;
    }
    
    default TypeBuilder<T,U> setSuffix(String suffix) {
	return this;
    }
    
    T buildType();
    
}
