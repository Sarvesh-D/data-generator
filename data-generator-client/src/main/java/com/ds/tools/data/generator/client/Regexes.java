/**
 *
 */
package com.ds.tools.data.generator.client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.RegexProperties;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.generators.RegexGenerator;
import com.ds.tools.data.generator.types.RegexType.RegexTypeBuilder;

/**
 * Class for generating Strings matching Regex Pattern
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public class Regexes implements Generator<Stream<String>> {

    private final RegexTypeBuilder regexTypeBuilder;

    private final Set<RegexProperties> regexProperties;

    private long quantity = Defaults.DEFAULT_QUANTITY;

    public Regexes() {
        regexTypeBuilder = new RegexTypeBuilder();
        regexTypeBuilder.setDataType(DataType.REGEX);
        regexProperties = new HashSet<>();
    }

    /**
     * Set the amount of data to be generated. If not supplied
     * {@link Defaults#DEFAULT_QUANTITY} will be generated
     *
     * @param qty
     * @return {@link Fakers} for chaining
     */
    public Regexes quantity(final long qty) {
        this.quantity = qty;
        return this;
    }

    /**
     * Set pattern for generating data
     *
     * @param regex
     * @return {@link Regexes} for chaining
     */
    public Regexes pattern(final String regex) {
        regexTypeBuilder.setRegex(regex);
        return this;
    }

    /**
     * {@inheritDoc} <br>
     * Generates Stream of Strings corresponding to set pattern
     */
    @Override
    public Stream<String> generate() {
        if (regexProperties.isEmpty()) {
            regexProperties.add(Defaults.DEFAULT_REGEX_PROP);
        }
        regexTypeBuilder.setTypeProperties(regexProperties);
        final RegexGenerator regexGenerator = RegexGenerator.of(regexTypeBuilder.buildType());
        return Stream.generate(regexGenerator::generate)
                     .limit(quantity);
    }

}
