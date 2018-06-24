package com.ds.tools.data.generator.generators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.common.DataGeneratorUtils;
import com.ds.tools.data.generator.exporters.DataExporter;
import com.ds.tools.data.generator.exporters.Exporter;
import com.ds.tools.data.generator.query.QueryRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * Core Class for Generating data. This class is responsible for executing the
 * data generation and collecting the data generated.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see DataExporter
 * @see Generator
 */
@Slf4j
public class DataGenerator implements Generator<Collection<DataCollector>> {

    private final List<QueryRunner> dataQueryRunners = new ArrayList<>();

    private final List<DataCollector> dataCollectors = new ArrayList<>();

    /**
     * Constructor for registering {@link #dataQueryRunners},
     * {@link #dataCollectors}
     *
     * @param dataGenQueries
     *            having the details for the queries
     */
    private DataGenerator(final String... dataGenQueries) {
        log.debug("Data Generation Queries : {}", Arrays.toString(dataGenQueries));
        registerDataQueryRunners(dataGenQueries);
    }

    /**
     * Factory method to get instance of DataGenerator
     *
     * @param dataGenQueries
     *            having the details for the queries
     * @return {@link DataGenerator}
     * @see DataGeneratorUtils#getDataGenQueries(String)
     */
    public static DataGenerator from(final String[] dataGenQueries) {
        return new DataGenerator(dataGenQueries);
    }

    /**
     * {@inheritDoc}. The data collected by this method be returned. To export the
     * data collected by this method use one of the {@link Exporter}
     *
     * @return This method will return Collection of data as collected by various
     *         dataCollectors.
     */
    @Override
    public Collection<DataCollector> generate() {
        final long start = System.nanoTime();
        log.info("beginning to generate data...");
        dataQueryRunners.forEach(queryRunner -> dataCollectors.add(queryRunner.run()));
        log.info("data generation completed...");
        final long end = System.nanoTime();
        final double timeTaken = (end - start) / 1000000000.0;
        log.info("Time taken to generate data : {} seconds", timeTaken);
        log.debug("Data Collected by {} data collectors.", dataCollectors.size());
        return dataCollectors;
    }

    /**
     * Registers the query runners for given dataGeneration queries. These query
     * runners are then invoked to start data generation process.
     *
     * @param dataGenQueries
     *            having the details for the queries
     */
    private void registerDataQueryRunners(final String[] dataGenQueries) {
        // build query runner for each data generate query and add to
        // dataQueryRunners
        Arrays.stream(dataGenQueries)
              .filter(query -> StringUtils.isNotEmpty(StringUtils.trimToEmpty(query)))
              .map(query -> QueryRunner.from(query))
              .forEach(dataQueryRunners::add);
        log.debug("Total Query Runners registered {}.", dataQueryRunners.size());
    }

}
