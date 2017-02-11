package com.cdk.ea.tools.data.generation.exception;

import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.core.Identifiers;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception class for any exception that might occur while interpreting
 * {@link DataType} for the data generation query
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class TypeInterpretationException extends DataGeneratorException {

    private static final long serialVersionUID = 1L;

    public TypeInterpretationException() {
	log.error(typeErrorMessage());
    }

    public TypeInterpretationException(String message) {
	log.error(message);
	log.error(typeErrorMessage());
    }

    private static String typeErrorMessage() {
	StringBuilder message = new StringBuilder();
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
