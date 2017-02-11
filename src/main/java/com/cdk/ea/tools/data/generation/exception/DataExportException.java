package com.cdk.ea.tools.data.generation.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception class for any exception that might occur while exporting data
 * generated.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class DataExportException extends DataGeneratorException {

    private static final long serialVersionUID = 1L;

    public DataExportException() {
	this("Exception occurred while exporting data.");
    }

    public DataExportException(String message) {
	log.error(message);
    }

    @Override
    public String getMessage() {
	return "Exception occurred while exporting data.";
    }

}
