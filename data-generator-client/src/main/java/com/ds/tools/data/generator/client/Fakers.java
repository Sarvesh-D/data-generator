/**
 *
 */
package com.ds.tools.data.generator.client;

import java.util.stream.Stream;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.FakerProperties;
import com.ds.tools.data.generator.generators.FakerGenerator;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.types.FakerType.FakerTypeBuilder;

/**
 * Class for generating sensible random data using Faker library
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public class Fakers implements Generator<Stream<String>> {

    private final FakerTypeBuilder fakerTypeBuilder;

    private FakerProperties fakerProperties;

    private long quantity = Defaults.DEFAULT_QUANTITY;

    public Fakers() {
        fakerTypeBuilder = new FakerTypeBuilder();
        fakerTypeBuilder.setDataType(DataType.FAKER);
    }

    /**
     * Set the amount of data to be generated. If not supplied
     * {@link Defaults#DEFAULT_QUANTITY} will be generated
     *
     * @param qty
     * @return {@link Fakers} for chaining
     */
    public Fakers quantity(final long qty) {
        this.quantity = qty;
        return this;
    }

    /**
     * Set the faker key for generating data of particular type
     *
     * @param fakerKey
     * @return {@link Fakers} for chaining
     */
    public Fakers fakerKey(final String fakerKey) {
        fakerProperties = new FakerProperties(fakerKey);
        return this;
    }

    /**
     * Set the locale for faker to generate locale specific data. This is optional.
     *
     * @param locale
     * @return {@link Fakers} for chaining
     */
    public Fakers locale(final String locale) {
        fakerTypeBuilder.setLocale(locale);
        return this;
    }

    /**
     * {@inheritDoc} <br>
     * Generates Stream of Strings
     */
    @Override
    public Stream<String> generate() {
        if (null == fakerProperties) {
            throw new IllegalStateException("Set Faker Key using fakerKey() method");
        }
        fakerTypeBuilder.setFakerProperties(fakerProperties);
        final FakerGenerator fakerGenerator = FakerGenerator.of(fakerTypeBuilder.buildType());
        return Stream.generate(fakerGenerator::generate)
                     .limit(quantity);
    }

}
