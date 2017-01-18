package com.cdk.ea.data.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.generators.Generator;

import lombok.Getter;
import lombok.Setter;

public abstract class Type {
    
    @Getter @Setter private Collection<Object> data;
    
    public abstract DataType getDataType();
    
    public abstract Set<? extends TypeProperties> getProperties();

    public abstract Generator<?> generator();
    
    public int getLength() {
	return 1;
    }
    
}
