package com.ds.tools.data.generator.exception;

import lombok.extern.slf4j.Slf4j;

/**
 * Root of Data Generator exceptions hierarchy.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class DataGeneratorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DataGeneratorException() {
        super();
    }

    public DataGeneratorException(final String message) {
        log.error(message);
    }

    @Override
    public String getMessage() {
        return "Exception occurred while generating data";
    }

}
