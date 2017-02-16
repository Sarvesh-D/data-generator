package com.cdk.ea.tools.data.generation.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception class for any exception that might occur while interpreting query.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class QueryInterpretationException extends DataGeneratorException {

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
