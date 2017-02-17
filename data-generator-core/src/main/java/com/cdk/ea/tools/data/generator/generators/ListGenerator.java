package com.cdk.ea.tools.data.generator.generators;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.cdk.ea.tools.data.generator.types.ListType;

import lombok.RequiredArgsConstructor;

/**
 * Generator class for generating random values from a set of pre-defined
 * values. This generator ensures that values generated are from a given set of
 * values.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see ListType
 */
@RequiredArgsConstructor
public class ListGenerator implements Generator<Object> {

    private final ListType listType;

    /**
     * Factory method to instantiate {@link ListGenerator}
     * 
     * @param listType
     *            from which the generator is to be instantiated.
     * @return {@link ListGenerator}
     */
    public static ListGenerator of(ListType listType) {
	assertNotNull("List Type cannot be null", listType);
	return new ListGenerator(listType);
    }

    /**
     * {@inheritDoc}. This generator picks up a random object from a pre-defined
     * list and return it.
     */
    @Override
    public Object generate() {
	List<Object> data = new ArrayList<>(listType.getData());
	return data.get(RandomUtils.nextInt(data.size()));
    }

}
