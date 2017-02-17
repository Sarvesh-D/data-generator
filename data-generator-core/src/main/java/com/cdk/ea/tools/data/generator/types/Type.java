package com.cdk.ea.tools.data.generator.types;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import com.cdk.ea.tools.data.generator.core.DataType;
import com.cdk.ea.tools.data.generator.generators.Generator;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract Type class providing a consistent interface to interact with various
 * {@link DataType} uniformly
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see ListType
 * @see NumberType
 * @see StringType
 * @see RegexType
 */
public abstract class Type {

    /**
     * Get any data that the type might hold. This method will return Null by
     * default. To be overriden for appropriate {@link DataType}
     */
    @Getter
    @Setter
    private Collection<Object> data;

    /**
     * @return the {@link Generator} for generating data for underlying
     *         {@link DataType}
     */
    public abstract Generator<?> generator();

    /**
     * @return the {@link DataType}
     */
    public abstract DataType getDataType();

    /**
     * @return the length for a single Data record. return 1 by default.
     */
    public int getLength() {
	return 1;
    }

    /**
     * @return Set of {@link TypeProperties} for underlying {@link DataType}.
     *         This method returns {@link Collections#EMPTY_SET} by default.
     */
    public Set<? extends TypeProperties> getProperties() {
	return Collections.EMPTY_SET;
    }

}
