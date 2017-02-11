package com.cdk.ea.tools.data.generation.exporters;

import java.util.Collection;

import com.cdk.ea.tools.data.generation.exception.DataExportException;
import com.cdk.ea.tools.data.generation.generators.DataCollector;

/**
 * Root interface for any Data Exporter.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 09-02-2017
 * @version 1.0
 * @see FileExporter
 * @see CSVFileExporter
 */
public interface DataExporter {

    /**
     * Exports the collected data from various data collectors defined in data
     * generation queries. Export destination depends on the implementation.
     * 
     * @param dataCollector
     *            Collection of one or more {@link DataCollector} whose data
     *            needs to be exported.
     * @throws DataExportException
     */
    void export(Collection<DataCollector> dataCollector) throws DataExportException;

}
