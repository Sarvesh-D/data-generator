package com.ds.tools.data.generator.query.interpreter;

/**
 * Factory Class for getting singleton instances of various {@link Interpreter}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 */
public enum Interpreters {

    /**
     * Holds singleton instance of {@link QueryInterpreter}
     */
    QUERY_INTERPRETER {

        private transient Interpreter queryInterpreter;

        @Override
        public Interpreter get() {
            /**
             * Must be initialized lazily since {@link QueryInterpreter} has dependency on
             * {@link Interpreters}. So eagerly initializing this {@link QueryInterpreter}
             * will cause circular dependency.
             */
            if (null == queryInterpreter) {
                queryInterpreter = new QueryInterpreter();
            }
            return queryInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link DataCollectorInterpreter}
     */
    DATA_COLLECTOR {

        private final transient Interpreter dataCollectorInterpreter = new DataCollectorInterpreter();

        @Override
        public Interpreter get() {
            return dataCollectorInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link GlobalDefaultOverrideInterpreter}
     */
    GLOBAL_DEFAULT_OVERRIDE {

        private final transient Interpreter globalOverrideInterpreter = new GlobalDefaultOverrideInterpreter();

        @Override
        public Interpreter get() {
            return globalOverrideInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link ListTypeInterpreter}
     */
    LIST_TYPE_INTERPRETER {

        private final transient Interpreter listTypeInterpreter = new ListTypeInterpreter();

        @Override
        public Interpreter get() {
            return listTypeInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link NumberTypeInterpreter}
     */
    NUMBER_TYPE_INTERPRETER {

        private final transient Interpreter numberTypeInterpreter = new NumberTypeInterpreter();

        @Override
        public Interpreter get() {
            return numberTypeInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link RegexTypeInterpreter}
     */
    REGEX_TYPE_INTERPRETER {

        private final transient Interpreter regexTypeInterpreter = new RegexTypeInterpreter();

        @Override
        public Interpreter get() {
            return regexTypeInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link StringTypeInterpreter}
     */
    STRING_TYPE_INTERPRETER {

        private final transient Interpreter stringTypeInterpreter = new StringTypeInterpreter();

        @Override
        public Interpreter get() {
            return stringTypeInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link TypeInterpreter}
     */
    TYPE_INTERPRETER {

        private final transient Interpreter typeInterpreter = new TypeInterpreter();

        @Override
        public Interpreter get() {
            return typeInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link QuantityInterpreter}
     */
    QUANTITY_INTERPRETER {

        private final transient Interpreter quantityInterpreter = new QuantityInterpreter();

        @Override
        public Interpreter get() {
            return quantityInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link DataCollectorInterpreter}
     */
    DATA_COLLECTOR_INTERPRETER {

        private final transient Interpreter dataCollectorInterpreter = new DataCollectorInterpreter();

        @Override
        public Interpreter get() {
            return dataCollectorInterpreter;
        }
    },

    /**
     * Holds singleton instance of {@link FakerTypeInterpreter}
     */
    FAKER_TYPE_INTERPRETER {

        private final transient Interpreter fakerTypeInterpreter = new FakerTypeInterpreter();

        @Override
        public Interpreter get() {
            return fakerTypeInterpreter;
        }
    };

    /**
     * @return corresponding {@link Interpreter} as backed by its Enum.
     */
    public abstract Interpreter get();

}
