package com.cdk.ea.data.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataGeneratorException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public DataGeneratorException() {
    }
    
    public DataGeneratorException(String message) {
	log.error(message);
    }

}