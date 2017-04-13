package com.cdk.ea.tools.data.generator.core;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.cdk.ea.tools.data.generator.common.DataGeneratorUtils;
import com.cdk.ea.tools.data.generator.exception.DataGeneratorException;
import com.cdk.ea.tools.data.generator.exporters.DataExporter;
import com.cdk.ea.tools.data.generator.generators.DataCollector;
import com.cdk.ea.tools.data.generator.generators.DataGenerator;
import com.cdk.ea.tools.data.generator.query.json.JsonQueryBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * Psuedo Main Class for starting data generation and export process. This class
 * is intended to be used by clients who want to use data-generator core module
 * in their projects.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 *
 * @since 16-02-2017
 * @version 1.0
 */
@Slf4j
public final class DataGenerationStarter {

    private static ConsoleAppender console = new ConsoleAppender();

    static {
	String pattern = "%-5p: %c - %m%n";
	console.setLayout(new PatternLayout(pattern));
	console.setThreshold(Level.INFO);
	console.activateOptions();
	Logger.getLogger("com.cdk.ea.tools.data.generator").addAppender(console);
    }

    private DataGenerationStarter() {
	// suppressing default constructor
    }

    /**
     * Psuedo main method to start data generation and export process for all
     * the CLI Queries passed. To get hold of generated data use
     * {@link DataGenerator#generate()} after instantiating
     * {@link DataGenerator} using factory method
     * {@link DataGenerator#from(String[])}
     * 
     * @param query
     *            Single CLI Query (may contain multiple queries in same String)
     */
    public static void start(String query) {
	if (StringUtils.contains(query, Constants.DEBUG_ENABLED))
	    console.setThreshold(Level.DEBUG);

	String[] cliQueries;
	long start = System.nanoTime();

	if (StringUtils.startsWith(query, Constants.JSON)) {
	    log.info("JSON format selected to generate data");
	    String[] jsonFiles = Arrays.stream(StringUtils.split(query))
		    .filter(arg -> arg.endsWith(Constants.JSON_EXTENSTION)).toArray(size -> new String[size]);
	    log.info("JSON files to generate data : {}", Arrays.toString(jsonFiles));
	    cliQueries = StringUtils.split(JsonQueryBuilder.getInstance().build(Arrays.asList(jsonFiles)),
		    Constants.CLI_QUERY_SEPARATOR);
	} else
	    cliQueries = StringUtils.split(StringUtils.join(query, Constants.SPACE), Constants.CLI_QUERY_SEPARATOR);

	if (ArrayUtils.isEmpty(cliQueries))
	    throw new DataGeneratorException(
		    "No queries supplied. You must supply atleast one CLI query or single JSON to proceed");

	log.debug("Total {} Queries passed to data-generator. Queries formed are {}", cliQueries.length,
		Arrays.toString(cliQueries));

	Arrays.stream(cliQueries).forEach(DataGenerationStarter::invokeDataGeneratorFor);

	long end = System.nanoTime();
	double timeTaken = (end - start) / 1000000000.0;

	log.info("Total Time Taken by data generator : {} seconds", timeTaken);
    }

    /**
     * Invokes {@link DataGenerator} for single CLI query. Generates data and
     * exports data (if specified) for single query. To get hold of generated
     * data use {@link DataGenerator#generate()}
     * 
     * @param cliQuery
     *            for which data generator should run
     */
    private static void invokeDataGeneratorFor(String cliQuery) {
	try {
	    long start = System.nanoTime();
	    log.debug("Invoking data generator for CLI query {}", cliQuery);
	    /*
	     * Default Override must happen before any data generation queries
	     * are interpreted and registered with query runners by
	     * DataGenerator.
	     */
	    DataGeneratorUtils.checkAndOverrideDataGeneratorDefaults(StringUtils.join(cliQuery, Constants.SPACE));

	    // generate data for cliQuery
	    Collection<DataCollector> collectedData = DataGenerator.from(DataGeneratorUtils.getDataGenQueries(cliQuery))
		    .generate();

	    // check and export data
	    if (DataGeneratorUtils.shouldExportToFile(cliQuery)) {
		DataExporter.from(DataGeneratorUtils.getDataExportQueries(cliQuery)).export(collectedData);
	    }

	    log.debug("data generator executed sucessfully for CLI query {}", cliQuery);
	    long end = System.nanoTime();
	    double timeTaken = (end - start) / 1000000000.0;
	    log.debug("Time Taken to execute CLI query {} : {} seconds", cliQuery, timeTaken);
	} catch (Exception e) {
	    log.error("Error Occured while invoking data generator for CLI query {} : {}", cliQuery, e.getMessage());
	} finally {
	    /*
	     * Restore Data Generator Defaults if overriden by
	     * DataGeneratorUtils.checkAndOverrideQueryDefaults for this
	     * cliqQuery so next cliQuery can use Data Generator Defaults in
	     * case it does not want to override them. Must be restored after
	     * current query has executed.
	     */
	    DataGeneratorUtils.restoreDataGeneratorDefaults();
	}
    }

}
