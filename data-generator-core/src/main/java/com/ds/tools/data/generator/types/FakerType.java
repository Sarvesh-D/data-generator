package com.ds.tools.data.generator.types;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.FakerProperties;
import com.ds.tools.data.generator.generators.FakerGenerator;
import com.ds.tools.data.generator.generators.Generator;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value Class for holding details of {@link DataType#FAKER}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 18-04-2017
 * @version 1.5
 * @see FakerTypeBuilder
 */
@Getter
@ToString
@Slf4j
public class FakerType extends Type {

    /**
     * Helper Builder Class for building instance of {@link FakerType}
     *
     * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
     * @since 18-04-2017
     * @version 1.5
     */
    public static final class FakerTypeBuilder implements TypeBuilder<FakerType, FakerProperties> {

        private DataType dataType;

        private FakerProperties fakerProperties;

        private String locale;

        @Override
        public FakerType buildType() {
            return new FakerType(this);
        }

        @Override
        public TypeBuilder<FakerType, FakerProperties> setDataType(final DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public TypeBuilder<FakerType, FakerProperties> setFakerProperties(final FakerProperties fakerProperties) {
            this.fakerProperties = fakerProperties;
            return this;
        }

        @Override
        public TypeBuilder<FakerType, FakerProperties> setLocale(final String locale) {
            this.locale = locale;
            return this;
        }

    }

    private final DataType dataType;

    private final FakerProperties fakerProperties;

    private final String locale;

    public FakerType(final FakerTypeBuilder fakerTypeBuilder) {
        this.dataType = fakerTypeBuilder.dataType;
        this.fakerProperties = fakerTypeBuilder.fakerProperties;
        this.locale = fakerTypeBuilder.locale;
        log.debug("Faker Type formed as {}", this);
    }

    @Override
    public Generator<String> generator() {
        return FakerGenerator.of(this);
    }

}
