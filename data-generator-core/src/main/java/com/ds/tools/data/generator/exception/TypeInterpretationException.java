package com.ds.tools.data.generator.exception;

import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Identifiers;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception class for any exception that might occur while interpreting
 * {@link DataType} for the data generation query
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class TypeInterpretationException extends DataGeneratorException {

    private static final long serialVersionUID = 1L;

    public TypeInterpretationException() {
        log.error(typeErrorMessage());
    }

    public TypeInterpretationException(final String message) {
        log.error(message);
        log.error(typeErrorMessage());
    }

    private static String typeErrorMessage() {
        final StringBuilder message = new StringBuilder();
        message.append("Usage:- " + Identifiers.TYPE.getIdentifier() + "<type>\n");
        message.append("types:\n");
        message.append(DataType.getEnumMap());
        return message.toString();
    }

    @Override
    public String getMessage() {
        return "Exception occurred while interpreting Data Type";
    }

}
