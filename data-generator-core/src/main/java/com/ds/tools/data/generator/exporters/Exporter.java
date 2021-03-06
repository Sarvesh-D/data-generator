package com.ds.tools.data.generator.exporters;

import java.util.Collection;

import com.ds.tools.data.generator.exception.DataExportException;
import com.ds.tools.data.generator.generators.DataCollector;

/**
 * Root interface for any Data Exporter.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 * @see FileExporter
 * @see CSVFileExporter
 */
public interface Exporter {

    /**
     * Exports the collected data from various data collectors defined in data
     * generation queries. Export destination depends on the implementation.
     *
     * @param dataCollector
     *            Collection of one or more {@link DataCollector} whose data needs
     *            to be exported.
     * @throws DataExportException
     *             if exception occurs while exporting data
     */
    void export(Collection<DataCollector> dataCollector) throws DataExportException;

}
