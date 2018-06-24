package com.ds.tools.data.generator.types;

import java.util.Set;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.NumberProperties;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.generators.NumberGenerator;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value Class for holding details of {@link DataType#NUMBER}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see NumberTypeBuilder
 */
@Getter
@ToString
@Slf4j
public class NumberType extends Type {

    /**
     * Helper Builder Class for building instance of {@link NumberType}
     *
     * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
     * @since 11-02-2017
     * @version 1.0
     */
    public static class NumberTypeBuilder implements TypeBuilder<NumberType, NumberProperties> {

        private DataType dataType;

        private Set<NumberProperties> properties;

        private int length;

        @Override
        public NumberType buildType() {
            return new NumberType(this);
        }

        @Override
        public TypeBuilder<NumberType, NumberProperties> setDataType(final DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        @Override
        public TypeBuilder<NumberType, NumberProperties> setLength(final int length) {
            this.length = length;
            return this;
        }

        @Override
        public TypeBuilder<NumberType, NumberProperties> setTypeProperties(final Set<NumberProperties> properties) {
            this.properties = properties;
            return this;
        }

    }

    private final DataType dataType;

    private final Set<NumberProperties> properties;

    private final int length;

    private NumberType(final NumberTypeBuilder builder) {
        this.dataType = builder.dataType;
        this.properties = builder.properties;
        this.length = builder.length;
        log.debug("Number type formed as : {}", this);
    }

    @Override
    public Generator<Number> generator() {
        return NumberGenerator.of(this);
    }

}
