package com.ds.tools.data.generator.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception class for any exception that might occur while interpreting query.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class QueryInterpretationException extends DataGeneratorException {

    private static final long serialVersionUID = 1L;

    public QueryInterpretationException() {
        this("Exception occurred while interpreting query");
    }

    public QueryInterpretationException(final String message) {
        log.error(message);
    }

    @Override
    public String getMessage() {
        return "Exception occurred while interpreting query";
    }

}
