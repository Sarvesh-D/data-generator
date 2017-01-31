package com.cdk.ea.tools.data.generation.exporters;

import java.util.Collection;

import com.cdk.ea.tools.data.generation.exception.DataExportException;
import com.cdk.ea.tools.data.generation.generators.DataCollector;

public interface DataExporter {
    
    void export(Collection<DataCollector> dataCollector) throws DataExportException;

}
