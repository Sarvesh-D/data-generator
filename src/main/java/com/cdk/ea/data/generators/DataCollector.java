package com.cdk.ea.data.generators;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;

@Data
public class DataCollector implements Comparable<DataCollector> {

    private final String name;
    // TODO should we make this a Set to collect only unique data?
    private Collection<Object> data = new ArrayList<>();
    
    @Override
    public int compareTo(DataCollector o) {
	return this.name.compareTo(o.getName());
    }
    
}
