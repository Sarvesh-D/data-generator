package com.cdk.ea.data.types;

import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.PatternStringProperties;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.generators.PatternStringGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class PatternStringType extends Type {
    
    private final DataType dataType;
    private final StringType stringType;
    private final Set<PatternStringProperties> properties;
    private final int length;
    private final String prefix;
    private final String suffix;
    
    private PatternStringType(PatternStringTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.stringType = builder.stringType;
	this.length = builder.length;
	this.prefix = builder.prefix;
	this.suffix = builder.suffix;
    }
    
    @Override
    public Generator<?> generator() {
	return PatternStringGenerator.of(this);
    }
    
    @ToString
    public static class PatternStringTypeBuilder implements TypeBuilder<PatternStringType,PatternStringProperties> {
	
	private DataType dataType;
	private StringType stringType;
	private Set<PatternStringProperties> properties;
	private int length;
	private String prefix;
	private String suffix;
	
	@Override
	public TypeBuilder<PatternStringType, PatternStringProperties> setDataType(DataType dataType) {
	    this.dataType = dataType;
	    return null;
	}
	
	@Override
	public TypeBuilder<PatternStringType, PatternStringProperties> setTypeProperties(Set<PatternStringProperties> properties) {
	    this.properties = properties;
	    return this;
	}
	
	public TypeBuilder<PatternStringType, PatternStringProperties> setStringType(StringType stringType) {
	    this.stringType = stringType;
	    return this;
	}
	
	@Override
	public TypeBuilder<PatternStringType, PatternStringProperties> setLength(int length) {
	    this.length = length;
	    return this;
	}
	
	@Override
	public TypeBuilder<PatternStringType, PatternStringProperties> setPrefix(String prefix) {
	    this.prefix = prefix;
	    return this;
	}
	
	@Override
	public TypeBuilder<PatternStringType, PatternStringProperties> setSuffix(String suffix) {
	    this.suffix = suffix;
	    return this;
	}
	
	@Override
	public PatternStringType buildType() {
	    return new PatternStringType(this);
	}

    }

}
