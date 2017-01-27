package com.cdk.ea.data.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryInterpretationException extends DataGeneratorException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public QueryInterpretationException() {
    }
    
    public QueryInterpretationException(String message) {
	log.error(message);
    }

}
