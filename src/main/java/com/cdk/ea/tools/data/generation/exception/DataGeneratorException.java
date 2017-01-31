package com.cdk.ea.tools.data.generation.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataGeneratorException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public DataGeneratorException() {
	super();
    }
    
    public DataGeneratorException(String message) {
	log.error(message);
    }
    
    @Override
    public String getMessage() {
        return "Exception occurred while generating data";
    }

}
