package com.cdk.ea.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;

import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.core.Identifiers;
import com.cdk.ea.tools.data.generator.query.Query.QueryBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Interpreter to override some of the default values that data-generator uses.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class GlobalDefaultOverrideInterpreter implements Interpreter {

    /**
     * {@inheritDoc}. This method looks up for any values that must be overriden
     * and accordingly updates them. Each time this method is invoked the
     * defaults of data-generator will be overriden accordingly. This
     * interpreters sole job is to override few of the default settings. These
     * default settings overriden by this interpreter applies to all the
     * queries, hence these should not be set individually in the queryBuilder.
     * These properties of corresponding interpreter should be set/overriden
     * instead.
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String overrideIdentifiers) {
	Assert.assertNull("QueryBuilder supplied to GlobalPropertyOverrideInterpreter should always be null",
		queryBuilder);

	Optional<Integer> quantityOverride = Arrays.stream(StringUtils.split(overrideIdentifiers, Constants.SPACE))
		.filter(i -> i.charAt(0) == Identifiers.QUANTITY.getIdentifier())
		.map(i -> Integer.valueOf(i.substring(1))).findFirst();
	if (quantityOverride.isPresent()) {
	    /*
	     * Need to override directly in Interpreters since Interpreters are
	     * registered as Singletons.
	     */
	    QuantityInterpreter.setDefaultQuantity(quantityOverride.get());
	    log.warn("Default quantity overriden to [{}]", quantityOverride.get());
	}

    }

}
