package com.cdk.ea.data.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueryInterpretationException extends DataGeneratorException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public QueryInterpretationException() {
	this("Exception occurred while interpreting query");
    }
    
    public QueryInterpretationException(String message) {
	log.error(message);
    }
    
    @Override
    public String getMessage() {
        return "Exception occurred while interpreting query";
    }

}
