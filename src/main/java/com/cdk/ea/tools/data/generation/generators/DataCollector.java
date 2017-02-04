package com.cdk.ea.tools.data.generation.generators;

import java.util.ArrayList;
import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "name")
@ToString(of = "name")
public class DataCollector implements Comparable<DataCollector> {

    private final String name;
    // TODO should we make this a Set to collect only unique data?
    private Collection<Object> data = new ArrayList<>();

    @Override
    public int compareTo(DataCollector o) {
	return this.name.compareTo(o.getName());
    }

}
