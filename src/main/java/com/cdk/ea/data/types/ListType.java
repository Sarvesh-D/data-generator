package com.cdk.ea.data.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.ListProperties;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.generators.ListGenerator;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class ListType extends Type {
    
    private final DataType dataType;
    private final Set<ListProperties> properties;
    private final Collection<Object> data;

    private ListType(ListTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.data = builder.data;
	log.debug("List type formed as : {}",this);
    }

    @Override
    public Generator<?> generator() {
	return ListGenerator.of(this);
    }
    
    public static class ListTypeBuilder implements TypeBuilder<ListType, ListProperties> {
	
	private DataType dataType;
	private Set<ListProperties> properties;
	private Collection<Object> data;

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

	@Override
	public TypeBuilder<ListType, ListProperties> setData(Collection<Object> data) {
	    this.data = data;
	    return this;
	}

	@Override
	public ListType buildType() {
	    return new ListType(this);
	}
	
    }
    

}
