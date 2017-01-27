package com.cdk.ea.data.generators;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.IntStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.types.ListType.ListTypeBuilder;

@RunWith(JUnit4.class)
public class ListGeneratorTest {
    
    private ListTypeBuilder listTypeBuilder;
    private Collection<Object> listData;

    @Before
    public void setUp() throws Exception {
	listTypeBuilder = (ListTypeBuilder) new ListTypeBuilder().setDataType(DataType.LIST);
	listData = new ArrayList<>(Arrays.asList("Sunday","Monday","Tuesday"));
	listTypeBuilder.setData(listData);
    }

    @After
    public void tearDown() throws Exception {
	listData = null;
	listTypeBuilder = null;
    }

    @Test
    public final void testGeneratedValues() {
	IntStream.range(0, 50).forEach(i -> assertTrue("Generated value must be from pre-defined list values", listData.contains(generate())));
    }
    
    private Object generate() {
	return listTypeBuilder.buildType().generator().generate();
    }

}
