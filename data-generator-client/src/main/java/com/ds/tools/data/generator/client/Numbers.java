/**
 *
 */
package com.ds.tools.data.generator.client;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.NumberProperties;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.generators.NumberGenerator;
import com.ds.tools.data.generator.types.NumberType.NumberTypeBuilder;

/**
 * Class to generate Numbers
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public class Numbers implements Generator<Stream<Number>> {

    private final NumberTypeBuilder numberTypeBuilder;

    private final Set<NumberProperties> numberProperties;

    private long quantity = Defaults.DEFAULT_QUANTITY;

    public Numbers() {
        numberTypeBuilder = new NumberTypeBuilder();
        numberTypeBuilder.setDataType(DataType.NUMBER);
        numberTypeBuilder.setLength(Defaults.DEFAULT_LENGTH);
        numberProperties = new HashSet<>();
    }

    /**
     * Sets the type of data to be generated as {@link Integer}
     *
     * @return {@link Numbers} for chaining
     */
    public Numbers integers() {
        numberProperties.add(NumberProperties.INTEGER);
        return this;
    }

    /**
     * Sets the length of Integer to be generated. If not supplied
     * {@link Defaults#DEFAULT_LENGTH} shall be used
     *
     * @param length
     * @return {@link Numbers} for chaining
     */
    public Numbers length(final int length) {
        numberTypeBuilder.setLength(length);
        return this;
    }

    /**
     * Set the amount of data to be generated. If not supplied
     * {@link Defaults#DEFAULT_QUANTITY} will be generated
     *
     * @param qty
     * @return {@link Fakers} for chaining
     */
    public Numbers quantity(final long qty) {
        this.quantity = qty;
        return this;
    }

    /**
     * {@inheritDoc} <br>
     * Generates Stream of Numbers
     */
    @Override
    public Stream<Number> generate() {
        if (numberProperties.isEmpty()) {
            numberProperties.add(Defaults.DEFAULT_NUMBER_PROP);
        }
        numberTypeBuilder.setTypeProperties(numberProperties);
        final NumberGenerator numberGenerator = NumberGenerator.of(numberTypeBuilder.buildType());
        return Stream.generate(numberGenerator::generate)
                     .limit(quantity);
    }

}
