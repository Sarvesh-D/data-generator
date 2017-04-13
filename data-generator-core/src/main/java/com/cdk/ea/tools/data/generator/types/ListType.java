package com.cdk.ea.tools.data.generator.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.tools.data.generator.core.DataType;
import com.cdk.ea.tools.data.generator.core.ListProperties;
import com.cdk.ea.tools.data.generator.generators.Generator;
import com.cdk.ea.tools.data.generator.generators.ListGenerator;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value Class for holding details of {@link DataType#LIST}
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see ListTypeBuilder
 */
@Getter
@ToString
@Slf4j
public class ListType extends Type {

    /**
     * Helper Builder Class for building instance of {@link ListType}
     * 
     * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
     * @since 11-02-2017
     * @version 1.0
     */
    public static class ListTypeBuilder implements TypeBuilder<ListType, ListProperties> {

	private DataType dataType;
	private Set<ListProperties> properties;
	@Getter
	private Collection<Object> data;

	@Override
	public ListType buildType() {
	    return new ListType(this);
	}

	@Override
	public TypeBuilder<ListType, ListProperties> setData(Collection<Object> data) {
	    this.data = data;
	    return this;
	}

	@Override
	public TypeBuilder<ListType, ListProperties> setDataType(DataType dataType) {
	    this.dataType = dataType;
	    return this;
	}

	@Override
	public TypeBuilder<ListType, ListProperties> setTypeProperties(Set<ListProperties> properties) {
	    this.properties = properties;
	    return this;
	}

    }

    private final DataType dataType;
    private final Set<ListProperties> properties;

    private final Collection<Object> data;

    private ListType(ListTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.data = builder.data;
	log.debug("List type formed as : {}", this);
    }

    @Override
    public Generator<?> generator() {
	return ListGenerator.of(this);
    }

}
