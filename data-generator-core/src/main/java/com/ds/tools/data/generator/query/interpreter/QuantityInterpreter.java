package com.ds.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.Identifiers;
import com.ds.tools.data.generator.query.Query;
import com.ds.tools.data.generator.query.Query.QueryBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Interpreter to interpret the quantity of data to be generated by a single
 * dataGeneration {@link Query}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class QuantityInterpreter implements Interpreter {

    @NonNull
    @Setter
    private static Integer defaultQuantity = Defaults.DEFAULT_QUANTITY;

    /**
     * {@inheritDoc}. This method looks up for quantity of the data to be generated.
     * If quantity is specified it is taken, else default quantity of
     * {@link Defaults#DEFAULT_QUANTITY} is taken. This method will override the
     * data quantity set by {@link GlobalDefaultOverrideInterpreter}
     */
    @Override
    public void doInterpret(final QueryBuilder queryBuilder, final String query) {
        try {
            // allow local quantity to override the default quantity
            final Optional<Integer> quantity = Arrays.stream(StringUtils.split(query))
                                                     .filter(i -> i.charAt(0) == Identifiers.QUANTITY.getIdentifier())
                                                     .map(i -> Integer.valueOf(i.substring(1)))
                                                     .findFirst();
            if (quantity.isPresent()) {
                queryBuilder.setQuantity(quantity.get());
            } else {
                log.debug("Quantity not specified. Default quantity of {} shall be used", defaultQuantity);
                queryBuilder.setQuantity(defaultQuantity);
            }
        } catch (final Exception e) {
            log.warn("Error occured while interpreting quantity : {}. Default quantity of {} shall be used", e.getMessage(), defaultQuantity);
            queryBuilder.setQuantity(defaultQuantity);
        }
    }

}
