package com.cdk.ea.tools.data.generator.types;

import java.util.Set;

import com.cdk.ea.tools.data.generator.core.DataType;
import com.cdk.ea.tools.data.generator.core.StringProperties;
import com.cdk.ea.tools.data.generator.generators.Generator;
import com.cdk.ea.tools.data.generator.generators.StringGenerator;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value Class for holding details of {@link DataType#STRING}
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see StringTypeBuilder
 */
@Getter
@ToString
@Slf4j
public class StringType extends Type {

    /**
     * Helper Builder Class for building instance of {@link StringType}
     * 
     * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
     * @since 11-02-2017
     * @version 1.0
     */
    public static class StringTypeBuilder implements TypeBuilder<StringType, StringProperties> {

	private DataType dataType;
	private Set<StringProperties> properties;
	private int length;
	private String prefix;
	private String suffix;

	@Override
	public StringType buildType() {
	    return new StringType(this);
	}

	@Override
	public TypeBuilder<StringType, StringProperties> setDataType(DataType dataType) {
	    this.dataType = dataType;
	    return this;
	}

	@Override
	public TypeBuilder<StringType, StringProperties> setLength(int length) {
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
	public TypeBuilder<StringType, StringProperties> setTypeProperties(Set<StringProperties> properties) {
	    this.properties = properties;
	    return this;
	}

    }

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
	log.debug("String type formed as : {}", this);
    }

    @Override
    public Generator<String> generator() {
	return StringGenerator.of(this);
    }

}
