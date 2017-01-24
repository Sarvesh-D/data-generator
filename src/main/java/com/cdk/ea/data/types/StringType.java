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
    private final String prefix;
    private final String suffix;

    private StringType(StringTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.length = builder.length;
	this.prefix = builder.prefix;
	this.suffix = builder.suffix;
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
	private String prefix;
	private String suffix;
	
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
	
	public TypeBuilder<StringType, StringProperties> setPrefix(String prefix) {
	    this.prefix = prefix;
	    return this;
	}
	
	public TypeBuilder<StringType, StringProperties> setSuffix(String suffix) {
	    this.suffix = suffix;
	    return this;
	}

	@Override
	public StringType buildType() {
	    return new StringType(this);
	}

    }

}
