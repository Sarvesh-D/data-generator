/**
 *
 */
package com.ds.tools.data.generator.rest.exception;

/**
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
public class BadRequestException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BadRequestException(final String message) {
        super(message);
    }

}
