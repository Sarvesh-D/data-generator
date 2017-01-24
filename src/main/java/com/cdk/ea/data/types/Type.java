package com.cdk.ea.data.types;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.generators.Generator;

import lombok.Getter;
import lombok.Setter;

public abstract class Type {
    
    @Getter @Setter private Collection<Object> data;
    
    public abstract DataType getDataType();
    
    public Set<? extends TypeProperties> getProperties() {
	return Collections.EMPTY_SET;
    }

    public abstract Generator<?> generator();
    
    public int getLength() {
	return 1;
    }
    
}
