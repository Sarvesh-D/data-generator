/**
 *
 */
package com.ds.tools.data.generator.client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.StringProperties;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.generators.StringGenerator;
import com.ds.tools.data.generator.types.StringType.StringTypeBuilder;

/**
 * Class for generating Strings
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public class Strings implements Generator<Stream<String>> {

    private final StringTypeBuilder stringTypeBuilder;

    private final Set<StringProperties> stringProperties;

    private long quantity = Defaults.DEFAULT_QUANTITY;

    public Strings() {
        stringTypeBuilder = new StringTypeBuilder();
        stringTypeBuilder.setDataType(DataType.STRING);
        stringTypeBuilder.setLength(Defaults.DEFAULT_LENGTH);
        stringProperties = new HashSet<>();
    }

    /**
     * Sets strings to be generated to contain alphabets
     *
     * @return {@link Strings} for chaining
     */
    public Strings alpha() {
        stringProperties.add(StringProperties.ALPHA);
        return this;
    }

    /**
     * Sets strings to be generated to contain numbers
     *
     * @return {@link Strings} for chaining
     */
    public Strings numeric() {
        stringProperties.add(StringProperties.NUMERIC);
        return this;
    }

    /**
     * Sets strings to be generated to contain special characters
     *
     * @return {@link Strings} for chaining
     */
    public Strings specialChars() {
        stringProperties.add(StringProperties.SPECIAL_CHARS);
        return this;
    }

    /**
     * Sets prefix to append to each string generated
     *
     * @param prefix
     * @return {@link Strings} for chaining
     */
    public Strings startingWith(final String prefix) {
        stringTypeBuilder.setPrefix(prefix);
        return this;
    }

    /**
     * Sets suffix to append to each string generated
     *
     * @param suffix
     * @return {@link Strings} for chaining
     */
    public Strings endingWith(final String suffix) {
        stringTypeBuilder.setSuffix(suffix);
        return this;
    }

    /**
     * Sets the length of String to be generated. If not supplied
     * {@link Defaults#DEFAULT_LENGTH} shall be used
     *
     * @param length
     * @return {@link Strings} for chaining
     */
    public Strings havingLength(final int length) {
        stringTypeBuilder.setLength(length);
        return this;
    }

    /**
     * Set the amount of data to be generated. If not supplied
     * {@link Defaults#DEFAULT_QUANTITY} will be generated
     *
     * @param qty
     * @return {@link Fakers} for chaining
     */
    public Strings quantity(final long qty) {
        this.quantity = qty;
        return this;
    }

    /**
     * {@inheritDoc} <br>
     * Generates Stream of Strings
     */
    @Override
    public Stream<String> generate() {
        if (stringProperties.isEmpty()) {
            stringProperties.add(Defaults.DEFAULT_STRING_PROP);
        }
        stringTypeBuilder.setTypeProperties(stringProperties);
        final StringGenerator stringGenerator = StringGenerator.of(stringTypeBuilder.buildType());
        return Stream.generate(stringGenerator::generate)
                     .limit(quantity);
    }

}
