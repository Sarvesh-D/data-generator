package com.cdk.ea.tools.data.generation.query.interpreter;

/**
 * Factory Class for getting singleton instances of various {@link Interpreter}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 10-02-2017
 * @version 1.0
 */
enum Interpreters {

    /**
     * Holds singleton instance of {@link TypeInterpreter}
     */
    TYPE_INTERPRETER {

	private final transient Interpreter typeInterpreter = new TypeInterpreter();

	@Override
	Interpreter get() {
	    return typeInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link QuantityInterpreter}
     */
    QUANTITY_INTERPRETER {

	private final transient Interpreter quantityInterpreter = new QuantityInterpreter();

	@Override
	Interpreter get() {
	    return quantityInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link DataCollectorInterpreter}
     */
    DATA_COLLECTOR_INTERPRETER {

	private final transient Interpreter dataCollectorInterpreter = new DataCollectorInterpreter();

	@Override
	Interpreter get() {
	    return dataCollectorInterpreter;
	}
    };

    /**
     * @return corresponding {@link Interpreter} as backed by its Enum.
     */
    abstract Interpreter get();

}
