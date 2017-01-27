package com.cdk.ea.data.exception;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.core.Properties;

import lombok.extern.slf4j.Slf4j;

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
	this();
	log.error(message);
    }
    
    private static String propertyErrorMessage() {
	StringBuilder message = new StringBuilder();
	message.append("Invalid Property\n");
	message.append("Usage:- "+Identifiers.PROPERTY.getIdentifier()+"<property>\n");
	message.append("properties:\n");
	message.append(Properties.ENUM_MAP);
	message.append("\n");
	return message.toString();
    }

}
