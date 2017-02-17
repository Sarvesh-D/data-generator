package com.cdk.ea.tools.data.generator.exporters;

/**
 * Interface for any exporter that will export data to file.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 * @see CSVFileExporter
 */
public interface FileExporter extends Exporter {

    /**
     * Sets the path to the file to which the data shall be written.
     * 
     * @param path
     *            of the file
     */
    void setFilePath(String path);

}
