package com.cdk.ea.tools.data.generation.query.interpreter;

import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.core.Properties;
import com.cdk.ea.tools.data.generation.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generation.exception.TypeInterpretationException;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generation.types.TypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Interpreter for interpreting the {@link DataType} details.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class TypeInterpreter implements Interpreter {

    /**
     * {@inheritDoc}. This method looks up for identifiers and populates the
     * {@link TypeBuilder} in {@link QueryBuilder}
     * 
     * @throws TypeInterpretationException
     *             if no or invalid {@link DataType} identifier is present.
     * @throws PropertiesInterpretationException
     *             if invalid {@link Properties} identifier are present.
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	DataType dataType = AbstractTypeInterpreter.getDataType(identifiers);
	log.debug("DataType set as : {}", dataType);
	dataType.getInterpreter().get().doInterpret(queryBuilder, identifiers);
    }

}
