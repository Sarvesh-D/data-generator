package com.cdk.ea.data.query.interpreter;

enum Interpreters {
    
    TYPE_INTERPRETER {
	
	private final transient Interpreter typeInterpreter = new TypeInterpreter();
	
	@Override
	Interpreter get() {
	    return typeInterpreter;
	}
    },
    
    QUANTITY_INTERPRETER {
	
	private final transient Interpreter quantityInterpreter = new QuantityInterpreter();
	
	@Override
	Interpreter get() {
	    return quantityInterpreter;
	}
    },
    
    DATA_COLLECTOR_INTERPRETER {
	
	private final transient Interpreter dataCollectorInterpreter = new DataCollectorInterpreter();
	
	@Override
	Interpreter get() {
	    return dataCollectorInterpreter;
	}
    };
    
    abstract Interpreter get();
    
}
