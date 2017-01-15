package com.cdk.ea.data.types;

import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.generators.Generator;

public abstract class Type {
    
    public abstract DataType getDataType();
    
    public abstract Set<? extends TypeProperties> getProperties();

    public abstract Generator<?> generator();
    
    public int getLength() {
	return 1;
    }

}
