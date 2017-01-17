package com.cdk.ea.data.exporters;

import java.util.Collection;

import com.cdk.ea.data.generators.DataCollector;

public interface DataExporter {
    
    void export(Collection<DataCollector> dataCollector);

}
