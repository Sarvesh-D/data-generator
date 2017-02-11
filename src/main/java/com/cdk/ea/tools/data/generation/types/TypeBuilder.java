package com.cdk.ea.tools.data.generation.types;

import java.util.Collection;
import java.util.Set;

import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.types.ListType.ListTypeBuilder;
import com.cdk.ea.tools.data.generation.types.NumberType.NumberTypeBuilder;
import com.cdk.ea.tools.data.generation.types.RegexType.RegexTypeBuilder;
import com.cdk.ea.tools.data.generation.types.StringType.StringTypeBuilder;

/**
 * Interface providing consistent way to interact with various Type Builders in
 * a uniform manner.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @param <T>
 *            {@link Type} that this Builder shall build
 * @param <U>
 *            {@link TypeProperties} for building the {@link Type}
 * @since 11-02-2017
 * @version 1.0
 * @see ListTypeBuilder
 * @see NumberTypeBuilder
 * @see StringTypeBuilder
 * @see RegexTypeBuilder
 */
public interface TypeBuilder<T extends Type, U extends TypeProperties> {

    /**
     * Builds the {@link Type}
     * 
     * @return {@link Type}
     */
    T buildType();

    /**
     * Sets the data for relevant {@link DataType}
     * 
     * @param data
     *            to be set
     * @return this
     */
    default TypeBuilder<T, U> setData(Collection<Object> data) {
	return this;
    }

    /**
     * Sets the {@link DataType}
     * 
     * @param dataType
     *            to be set
     * @return this
     */
    TypeBuilder<T, U> setDataType(DataType dataType);

    /**
     * Sets the length of data
     * 
     * @param length
     *            of data to be set
     * @return this
     */
    default TypeBuilder<T, U> setLength(int length) {
	return this;
    }

    /**
     * Sets the {@link TypeProperties}
     * 
     * @param properties
     *            to be set
     * @return this
     */
    default TypeBuilder<T, U> setTypeProperties(Set<U> properties) {
	return this;
    }

}
