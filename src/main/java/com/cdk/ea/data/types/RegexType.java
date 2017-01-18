package com.cdk.ea.data.types;

import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.RegexProperties;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.generators.RegexGenerator;
import com.mifmif.common.regex.Generex;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class RegexType extends Type {
    
    private final DataType dataType;
    private final Set<RegexProperties> properties;
    private final Generex regex;
    
    private RegexType(RegexTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.regex = builder.regex;
    }

    @Override
    public Generator<?> generator() {
	return RegexGenerator.from(this);
    }
    
    @ToString
    public static class RegexTypeBuilder implements TypeBuilder<RegexType, RegexProperties> {
	
	private DataType dataType;
	private Set<RegexProperties> properties;
	private Generex regex;

	@Override
	public TypeBuilder<RegexType, RegexProperties> setDataType(DataType dataType) {
	    this.dataType = dataType;
	    return this;
	}

	@Override
	public RegexType buildType() {
	    return new RegexType(this);
	}
	
	public TypeBuilder<RegexType, RegexProperties> setRegex(String regex) {
	    this.regex = new Generex(regex);
	    return this;
	}
	
    }

}