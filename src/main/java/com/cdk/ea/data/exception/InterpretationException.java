package com.cdk.ea.data.exception;

import java.util.logging.Level;

import lombok.extern.java.Log;

@Log
public class InterpretationException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public InterpretationException(String message) {
	log.log(Level.SEVERE, message);
    }

}
