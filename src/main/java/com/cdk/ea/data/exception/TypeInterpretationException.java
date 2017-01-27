package com.cdk.ea.data.exception;

import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.Identifiers;

public class TypeInterpretationException extends DataGeneratorException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public TypeInterpretationException() {
	super(typeErrorMessage());
    }
    
    private static String typeErrorMessage() {
   	StringBuilder message = new StringBuilder();
   	message.append("Invalid Type\n");
   	message.append("Usage:- "+Identifiers.TYPE.getIdentifier()+"<type>\n");
   	message.append("types:\n");
   	message.append(DataType.ENUM_MAP);
   	message.append("\n");
   	return message.toString();
       }

}
