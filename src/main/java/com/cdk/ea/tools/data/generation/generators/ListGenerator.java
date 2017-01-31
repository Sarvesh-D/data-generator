package com.cdk.ea.tools.data.generation.generators;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import com.cdk.ea.tools.data.generation.types.ListType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListGenerator implements Generator<Object> {
    
    private final ListType listType;

    @Override
    public Object generate() {
	List<Object> data = new ArrayList<>(listType.getData());
	return data.get(RandomUtils.nextInt(data.size()));
    }
    
    public static ListGenerator of(ListType listType) {
	assertNotNull("List Type cannot be null", listType);
	return new ListGenerator(listType);
    }

}
