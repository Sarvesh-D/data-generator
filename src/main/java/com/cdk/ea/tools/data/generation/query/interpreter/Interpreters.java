package com.cdk.ea.tools.data.generation.query.interpreter;

/**
 * Factory Class for getting singleton instances of various {@link Interpreter}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 10-02-2017
 * @version 1.0
 */
public enum Interpreters {

    /**
     * Holds singleton instance of {@link QueryInterpreter}
     */
    QUERY_INTERPRETER {

	private Interpreter queryInterpreter;

	@Override
	public Interpreter get() {
	    /**
	     * Must be initialized lazily since {@link QueryInterpreter} has
	     * dependency on {@link Interpreters}. So eagerly initializing this
	     * {@link QueryInterpreter} will cause circular dependency.
	     */
	    if (null == queryInterpreter)
		queryInterpreter = new QueryInterpreter();
	    return queryInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link DataCollectorInterpreter}
     */
    DATA_COLLECTOR {

	private final Interpreter dataCollectorInterpreter = new DataCollectorInterpreter();

	@Override
	public Interpreter get() {
	    return dataCollectorInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link GlobalDefaultOverrideInterpreter}
     */
    GLOBAL_DEFAULT_OVERRIDE {

	private final Interpreter globalOverrideInterpreter = new GlobalDefaultOverrideInterpreter();

	@Override
	public Interpreter get() {
	    return globalOverrideInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link ListTypeInterpreter}
     */
    LIST_TYPE_INTERPRETER {

	private final Interpreter listTypeInterpreter = new ListTypeInterpreter();

	@Override
	public Interpreter get() {
	    return listTypeInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link NumberTypeInterpreter}
     */
    NUMBER_TYPE_INTERPRETER {

	private final Interpreter numberTypeInterpreter = new NumberTypeInterpreter();

	@Override
	public Interpreter get() {
	    return numberTypeInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link RegexTypeInterpreter}
     */
    REGEX_TYPE_INTERPRETER {

	private final Interpreter regexTypeInterpreter = new RegexTypeInterpreter();

	@Override
	public Interpreter get() {
	    return regexTypeInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link StringTypeInterpreter}
     */
    STRING_TYPE_INTERPRETER {

	private final Interpreter stringTypeInterpreter = new StringTypeInterpreter();

	@Override
	public Interpreter get() {
	    return stringTypeInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link TypeInterpreter}
     */
    TYPE_INTERPRETER {

	private final Interpreter typeInterpreter = new TypeInterpreter();

	@Override
	public Interpreter get() {
	    return typeInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link QuantityInterpreter}
     */
    QUANTITY_INTERPRETER {

	private final Interpreter quantityInterpreter = new QuantityInterpreter();

	@Override
	public Interpreter get() {
	    return quantityInterpreter;
	}
    },

    /**
     * Holds singleton instance of {@link DataCollectorInterpreter}
     */
    DATA_COLLECTOR_INTERPRETER {

	private final Interpreter dataCollectorInterpreter = new DataCollectorInterpreter();

	@Override
	public Interpreter get() {
	    return dataCollectorInterpreter;
	}
    };

    /**
     * @return corresponding {@link Interpreter} as backed by its Enum.
     */
    public abstract Interpreter get();

}
