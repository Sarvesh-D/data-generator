package com.cdk.ea.tools.data.generation.exception;

import com.cdk.ea.tools.data.generation.core.Identifiers;
import com.cdk.ea.tools.data.generation.core.Properties;

import lombok.extern.slf4j.Slf4j;

/**
 * Exception class for any exception that might occur while interpreting
 * {@link Properties}.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 09-02-2017
 * @version 1.0
 */
@Slf4j
public class PropertiesInterpretationException extends DataGeneratorException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public PropertiesInterpretationException() {
	log.error(propertyErrorMessage());
    }

    public PropertiesInterpretationException(String message) {
	log.error(message);
	log.error(propertyErrorMessage());
    }

    private static String propertyErrorMessage() {
	StringBuilder message = new StringBuilder();
	message.append("Invalid Property\n");
	message.append("Usage:- " + Identifiers.PROPERTY.getIdentifier() + "<property>\n");
	message.append("properties:\n");
	message.append(Properties.getEnumMap());
	return message.toString();
    }

    @Override
    public String getMessage() {
	return "Exception occurred while interpreting properties";
    }

}
