package com.cdk.ea.tools.data.generation.generators;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.types.ListType.ListTypeBuilder;

/**
 * Test Class for testing the {@link ListGenerator}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class ListGeneratorTest {

    private ListTypeBuilder listTypeBuilder;
    private Collection<Object> listData;

    @Before
    public void setUp() throws Exception {
	listTypeBuilder = (ListTypeBuilder) new ListTypeBuilder().setDataType(DataType.LIST);
	listData = new ArrayList<>(Arrays.asList("Sunday", "Monday", "Tuesday"));
	listTypeBuilder.setData(listData);
    }

    @After
    public void tearDown() throws Exception {
	listData = null;
	listTypeBuilder = null;
    }

    @Test
    public final void testGeneratedValues() {
	ListGenerator listGenerator = getListGenerator();
	IntStream.range(0, 50).forEach(
		i -> assertTrue("Generated value must be from pre-defined list values", listData.contains(listGenerator.generate())));
    }

    private ListGenerator getListGenerator() {
	return ListGenerator.of(listTypeBuilder.buildType());
    }

}
