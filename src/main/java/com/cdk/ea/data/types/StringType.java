package com.cdk.ea.data.types;

import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.StringProperties;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.generators.StringGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class StringType extends Type {

    private final DataType dataType;
    private final Set<StringProperties> properties;
    private final int length;

    private StringType(StringTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.length = builder.length;
    }
    
    @Override
    public Generator<String> generator() {
	return StringGenerator.of(this);
    }
    
    @ToString
    public static class StringTypeBuilder implements TypeBuilder<StringType,StringProperties> {
	
	private DataType dataType;
	private Set<StringProperties> properties;
	private int length;
	
	@Override
	public TypeBuilder<StringType,StringProperties> setDataType(DataType dataType) {
	    this.dataType = dataType;
	    return this;
	}
	
	@Override
	public TypeBuilder<StringType,StringProperties> setTypeProperties(Set<StringProperties> properties) {
	    this.properties = properties;
	    return this;
	}

	@Override
	public TypeBuilder<StringType,StringProperties> setLength(int length) {
	    this.length = length;
	    return this;
	}

	@Override
	public StringType buildType() {
	    return new StringType(this);
	}

    }

}
