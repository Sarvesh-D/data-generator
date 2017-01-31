package com.cdk.ea.tools.data.generation.types;

import java.util.Set;

import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.core.RegexProperties;
import com.cdk.ea.tools.data.generation.generators.Generator;
import com.cdk.ea.tools.data.generation.generators.RegexGenerator;
import com.mifmif.common.regex.Generex;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class RegexType extends Type {
    
    private final DataType dataType;
    private final Set<RegexProperties> properties;
    private final Generex regex;
    
    private RegexType(RegexTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.regex = builder.regex;
	log.debug("Regex type formed as : {}",this);
    }

    @Override
    public Generator<String> generator() {
	return RegexGenerator.from(this);
    }
    
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
