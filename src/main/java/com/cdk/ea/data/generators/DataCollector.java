package com.cdk.ea.data.generators;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Getter;
import lombok.ToString;

@ToString
public class DataCollector {
    
    // TODO should we make this a Set to collect only unique data?
    @Getter
    private Collection<Object> data = new ArrayList<>();
    
}
