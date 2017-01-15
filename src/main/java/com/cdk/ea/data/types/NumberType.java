package com.cdk.ea.data.types;

import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.NumberProperties;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.generators.NumberGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class NumberType extends Type {
    
    private final DataType dataType;
    private final Set<NumberProperties> properties;
    private final int length;

    private NumberType(NumberTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.length = builder.length;
    }
    
    @Override
    public Generator<Number> generator() {
	return NumberGenerator.of(this);
    }
    
    @ToString
    public static class NumberTypeBuilder implements TypeBuilder<NumberType,NumberProperties> {
	
	private DataType dataType;
	private Set<NumberProperties> properties;
	private int length;
	
	@Override
	public TypeBuilder<NumberType,NumberProperties> setDataType(DataType dataType) {
	    this.dataType = dataType;
	    return this;
	}
	
	@Override
	public TypeBuilder<NumberType,NumberProperties> setTypeProperties(Set<NumberProperties> properties) {
	    this.properties = properties;
	    return this;
	}

	@Override
	public TypeBuilder<NumberType,NumberProperties> setLength(int length) {
	    this.length = length;
	    return this;
	}

	@Override
	public NumberType buildType() {
	    return new NumberType(this);
	}

    }

}
