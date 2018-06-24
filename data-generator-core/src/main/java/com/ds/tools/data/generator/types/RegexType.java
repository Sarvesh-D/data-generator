package com.ds.tools.data.generator.types;

import java.util.Set;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.RegexProperties;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.generators.RegexGenerator;
import com.mifmif.common.regex.Generex;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * Value Class for holding details of {@link DataType#REGEX}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see RegexTypeBuilder
 */
@Getter
@ToString
@Slf4j
public class RegexType extends Type {

    /**
     * Helper Builder Class for building instance of {@link RegexType}
     *
     * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
     * @since 11-02-2017
     * @version 1.0
     */
    public static class RegexTypeBuilder implements TypeBuilder<RegexType, RegexProperties> {

        private DataType dataType;

        private Set<RegexProperties> properties;

        private Generex regex;

        @Override
        public RegexType buildType() {
            return new RegexType(this);
        }

        @Override
        public TypeBuilder<RegexType, RegexProperties> setDataType(final DataType dataType) {
            this.dataType = dataType;
            return this;
        }

        public TypeBuilder<RegexType, RegexProperties> setRegex(final String regex) {
            this.regex = new Generex(regex);
            return this;
        }

    }

    private final DataType dataType;

    private final Set<RegexProperties> properties;

    private final Generex regex;

    private RegexType(final RegexTypeBuilder builder) {
        this.dataType = builder.dataType;
        this.properties = builder.properties;
        this.regex = builder.regex;
        log.debug("Regex type formed as : {}", this);
    }

    @Override
    public Generator<String> generator() {
        return RegexGenerator.of(this);
    }

}
