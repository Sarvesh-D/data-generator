/**
 *
 */
package com.ds.tools.data.generator.client;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.ListProperties;
import com.ds.tools.data.generator.generators.Generator;
import com.ds.tools.data.generator.generators.ListGenerator;
import com.ds.tools.data.generator.types.ListType.ListTypeBuilder;

/**
 * Class for generating Data from Set of pre-defined collection
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public class Lists implements Generator<Stream<Object>> {

    private final ListTypeBuilder listTypeBuilder;

    private final Set<ListProperties> listProperties;

    private long quantity = Defaults.DEFAULT_QUANTITY;

    public Lists() {
        listTypeBuilder = new ListTypeBuilder();
        listTypeBuilder.setDataType(DataType.LIST);
        listProperties = new HashSet<>();
    }

    /**
     * Set the amount of data to be generated. If not supplied
     * {@link Defaults#DEFAULT_QUANTITY} will be generated
     *
     * @param qty
     * @return {@link Lists} for chaining
     */
    public Lists quantity(final long qty) {
        this.quantity = qty;
        return this;
    }

    /**
     * Set the pre-defined data to generate data from
     *
     * @param data
     * @return {@link Lists} for chaining
     */
    public Lists custom(final Collection<Object> data) {
        listProperties.add(ListProperties.CUSTOM);
        listTypeBuilder.setData(data);
        return this;
    }

    /**
     * {@inheritDoc} <br>
     * Generates Stream of Objects
     */
    @Override
    public Stream<Object> generate() {
        if (null == listTypeBuilder.getData() || listTypeBuilder.getData()
                                                                .isEmpty()) {
            throw new IllegalStateException("No Data supplied to list. Supply data using custom() method");
        }
        if (listProperties.isEmpty()) {
            listProperties.add(ListProperties.CUSTOM);
        }
        listTypeBuilder.setTypeProperties(listProperties);
        final ListGenerator listGenerator = ListGenerator.of(listTypeBuilder.buildType());
        return Stream.generate(listGenerator::generate)
                     .limit(quantity);
    }

}
