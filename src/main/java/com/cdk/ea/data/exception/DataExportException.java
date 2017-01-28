package com.cdk.ea.data.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataExportException extends DataGeneratorException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public DataExportException() {
	super();
    }
    
    public DataExportException(String message) {
	log.error(message);
    }

}
