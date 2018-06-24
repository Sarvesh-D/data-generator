package com.ds.tools.data.generator.query.interpreter;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.Identifiers;
import com.ds.tools.data.generator.generators.DataCollector;
import com.ds.tools.data.generator.query.Query;
import com.ds.tools.data.generator.query.Query.QueryBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Interpreter to interpret the name of the {@link DataCollector} for a single
 * DataGeneration {@link Query}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class DataCollectorInterpreter implements Interpreter {

    /**
     * {@inheritDoc}. This method interprets the {@link DataCollector} name and
     * populates {@link QueryBuilder#setDataCollector(DataCollector)}. If no name is
     * identified sets the default name as
     * {@link Defaults#DEFAULT_DATA_COLLECTOR_NAME}
     */
    @Override
    public void doInterpret(final QueryBuilder queryBuilder, final String query) {
        try {
            final Optional<String> dataCollectorName = Arrays.stream(StringUtils.split(query))
                                                             .filter(i -> i.charAt(0) == Identifiers.DATA_COLLECTOR_PREFIX.getIdentifier())
                                                             .map(i -> i.substring(1))
                                                             .findFirst();
            String collectorName;
            if (dataCollectorName.isPresent()) {
                collectorName = dataCollectorName.get();
                log.debug("Data Collector with name [{}] shall be regiesterd", collectorName);
            } else {
                collectorName = Defaults.DEFAULT_DATA_COLLECTOR_NAME;
                log.warn("No Data Collector name specified. Default data collector name [{}] shall be used to register data collector.", Defaults.DEFAULT_DATA_COLLECTOR_NAME);
            }
            queryBuilder.setDataCollector(new DataCollector(collectorName));
        } catch (final Exception e) {
            log.error("Error occured while interpreting data collector name. Default data collector name [{}] shall be used to register data collector.", Defaults.DEFAULT_DATA_COLLECTOR_NAME);
        }
    }

}
