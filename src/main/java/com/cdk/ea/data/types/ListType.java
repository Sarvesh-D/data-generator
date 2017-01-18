package com.cdk.ea.data.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.ListProperties;
import com.cdk.ea.data.generators.Generator;
import com.cdk.ea.data.generators.ListGenerator;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper=false)
public class ListType extends Type {
    
    private final DataType dataType;
    private final Set<ListProperties> properties;
    private final Collection<Object> data;

    private ListType(ListTypeBuilder builder) {
	this.dataType = builder.dataType;
	this.properties = builder.properties;
	this.data = builder.data;
    }

    @Override
    public Generator<?> generator() {
	return ListGenerator.of(this);
    }
    
    @ToString
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
	public TypeBuilder<ListType, ListProperties> setLength(int length) {
	    // TODO check if ListType can make use of length property
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
