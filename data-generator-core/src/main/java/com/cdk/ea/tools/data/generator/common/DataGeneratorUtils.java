package com.cdk.ea.tools.data.generator.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.core.Defaults;
import com.cdk.ea.tools.data.generator.core.Identifiers;
import com.cdk.ea.tools.data.generator.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generator.generators.DataGenerator;
import com.cdk.ea.tools.data.generator.query.interpreter.Interpreters;

import lombok.extern.slf4j.Slf4j;

/**
 * Utility class for {@link DataGenerator}
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 *
 * @since 16-02-2017
 * @version 1.0
 */
@Slf4j
public enum DataGeneratorUtils {
    ;

    /**
     * Checks and invokes {@link Interpreters#GLOBAL_DEFAULT_OVERRIDE} if global
     * override flag is present
     * 
     * @param cmdLineQuery
     *            to check for global override
     */
    public static void checkAndOverrideDataGeneratorDefaults(String cmdLineQuery) {
	log.debug("checking for global overrides flag if present");
	// see if there are global overrides
	if (cmdLineQuery.contains(Constants.GLOBAL_OVERRIDE)) {
	    log.debug("Query contains global override flag {}.", Constants.GLOBAL_OVERRIDE);
	    Interpreters.GLOBAL_DEFAULT_OVERRIDE.get().doInterpret(null,
		    StringUtils.substringAfter(cmdLineQuery, Constants.GLOBAL_OVERRIDE));
	}
    }

    /**
     * Extracts one or more dataExportQueries from the complete CLI Query
     * passed.
     * 
     * @param cmdLineQuery
     *            to extract data export queries from
     * @return array containing one or more dataExport queries.
     */
    public static String[] getDataExportQueries(String cmdLineQuery) {
	String completeDataExportQueryString = StringUtils.substringBetween(cmdLineQuery,
		Identifiers.DATA_EXPORT_QUERY_PREFIX.getIdentifier().toString(),
		Identifiers.DATA_EXPORT_QUERY_SUFFIX.getIdentifier().toString());

	// gather all CMD queries to export data
	String[] dataExportQueries = StringUtils.split(completeDataExportQueryString,
		Identifiers.QUERY_SEPARATOR.getIdentifier());

	if (org.apache.commons.lang3.ArrayUtils.isEmpty(dataExportQueries))
	    throw new QueryInterpretationException(
		    "No Data Export queries found. Specify data generation queries within <...>");
	return dataExportQueries;
    }

    /**
     * Extracts one or more dataGenerationQueries from the complete CLI Query
     * passed.
     * 
     * @param cmdLineQuery
     *            to extract data generation queries from
     * @return array containing one or more dataGeneration queries.
     */
    public static String[] getDataGenQueries(String cmdLineQuery) {
	String completeDataGenQueryString = StringUtils.substringBetween(cmdLineQuery,
		Identifiers.DATA_GEN_QUERY_PREFIX.getIdentifier().toString(),
		Identifiers.DATA_GEN_QUERY_SUFFIX.getIdentifier().toString());

	// gather all CMD queries to generate data
	String[] dataGenQueries = StringUtils.split(completeDataGenQueryString,
		Identifiers.QUERY_SEPARATOR.getIdentifier());

	if (org.apache.commons.lang3.ArrayUtils.isEmpty(dataGenQueries))
	    throw new QueryInterpretationException(
		    "No Data Generation queries found. Specify data generation queries within (...)");
	return dataGenQueries;
    }

    /**
     * Restores the defaults for data generator that might have been overriden
     */
    public static void restoreDataGeneratorDefaults() {
	log.debug("restoring data generator defaults");
	String overrideIdentifiers = Identifiers.QUANTITY.getIdentifier() + String.valueOf(Defaults.DEFAULT_QUANTITY);
	Interpreters.GLOBAL_DEFAULT_OVERRIDE.get().doInterpret(null, overrideIdentifiers);
	log.debug("data generator defaults restored");
    }

    /**
     * Checks the CLI query for data export flag
     * 
     * @param cmdLineQuery
     *            to check for data export
     * @return <code>True</code> if data export flag is present,
     *         <code>False</code> otherwise.
     */
    public static boolean shouldExportToFile(String cmdLineQuery) {
	// check query for any data exporters
	boolean exportToFile = ArrayUtils.contains(StringUtils.split(cmdLineQuery),
		Identifiers.FILE.getIdentifier().toString());
	log.info("Export data to CSV set to {}", exportToFile);
	return exportToFile;
    }

}
