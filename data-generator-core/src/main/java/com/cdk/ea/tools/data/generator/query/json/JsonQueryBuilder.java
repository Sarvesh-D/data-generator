package com.cdk.ea.tools.data.generator.query.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generator.common.Builder;
import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.core.DataType;
import com.cdk.ea.tools.data.generator.core.Identifiers;
import com.cdk.ea.tools.data.generator.core.Properties;
import com.cdk.ea.tools.data.generator.exception.QueryInterpretationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Builder Class responsible for converting the JSON file(s) to CLI query(s)
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JsonQueryBuilder implements Builder<String, List<String>> {

    @Getter
    private static final JsonQueryBuilder instance = new JsonQueryBuilder();

    /**
     * Builds a single CLI query by transforming each JSON file to CLI query and
     * then concatenating them.
     * 
     * @param jsonFiles
     *            one or more JSON file holding query.
     * @throws QueryInterpretationException
     *             if problem occurs while building CLI query from JSON.
     */
    @Override
    public String build(List<String> jsonFiles) {
	final long start = System.nanoTime();
	List<String> cliQueries = new ArrayList<>();
	jsonFiles.forEach(jsonFile -> cliQueries.add(buildCLIQueryFrom(jsonFile)));
	String finalCLIQuery = cliQueries.stream().collect(Collectors.joining(Constants.CLI_QUERY_SEPARATOR));
	final long end = System.nanoTime();
	final double timeTaken = (end - start) / 1000000000.0;
	log.info("Time taken to parse all JSONs to CLI Query : {} seconds", timeTaken);
	return finalCLIQuery;
    }

    private void appendCSVColumnDetails(StringBuilder cmdQueryBuilder, List<CsvColumnDetails> csvColumnDetails) {
	csvColumnDetails.stream().forEach(csvColumnDetail -> {
	    cmdQueryBuilder.append(Identifiers.CSV_HEADER_PREFIX.getIdentifier());
	    cmdQueryBuilder.append(csvColumnDetail.getHeaderName());
	    cmdQueryBuilder.append(Constants.SPACE);
	    cmdQueryBuilder.append(Identifiers.CSV_COL_DATA_REF.getIdentifier());
	    cmdQueryBuilder.append(csvColumnDetail.getDataRef().stream().collect(Collectors.joining(Constants.COMMA)));
	    cmdQueryBuilder.append(Constants.SPACE);
	});
    }

    private void appendCSVFileName(StringBuilder cmdQueryBuilder, String csvFileName) {
	cmdQueryBuilder.append(csvFileName);
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendCustomListVals(StringBuilder cmdQueryBuilder, List<String> list) {
	String listVals = list.stream().collect(Collectors.joining(Constants.COMMA, Constants.CUSTOM_LIST_VALS_PREFIX,
		Constants.CUSTOM_LIST_VALS_SUFFIX));
	cmdQueryBuilder.append(listVals);
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendDataCollectorName(StringBuilder cmdQueryBuilder, String collectorName) {
	cmdQueryBuilder.append(Identifiers.DATA_COLLECTOR_PREFIX.getIdentifier());
	cmdQueryBuilder.append(collectorName);
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendDataDetails(StringBuilder cmdQueryBuilder, DataDetails dataDetails) {
	// add data type
	appendDataType(cmdQueryBuilder, dataDetails.getType());

	// add data properties
	appendDataProperties(cmdQueryBuilder, dataDetails.getProperties());

	// add data length
	appendDataLength(cmdQueryBuilder, dataDetails.getLength());

	// get custom list values if present
	if (null != dataDetails.getList()) {
	    appendCustomListVals(cmdQueryBuilder, dataDetails.getList());
	}

	// get regex if present
	if (StringUtils.isNotBlank(dataDetails.getRegex())) {
	    appendRegex(cmdQueryBuilder, dataDetails.getRegex());
	}

	// get prefix if present
	if (StringUtils.isNotBlank(dataDetails.getPrefix())) {
	    appendPrefix(cmdQueryBuilder, dataDetails.getPrefix());
	}

	// get suffix if present
	if (StringUtils.isNotBlank(dataDetails.getSuffix())) {
	    appendSuffix(cmdQueryBuilder, dataDetails.getSuffix());
	}
    }

    private void appendDataLength(StringBuilder cmdQueryBuilder, int quantity) {
	cmdQueryBuilder.append(Identifiers.LENGTH.getIdentifier());
	cmdQueryBuilder.append(quantity);
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendDataProperties(StringBuilder cmdQueryBuilder, Set<String> properties) {
	properties.stream().forEach(property -> {
	    cmdQueryBuilder.append(Identifiers.PROPERTY.getIdentifier());
	    cmdQueryBuilder.append(Properties.valueOf(property).getIdentifier());
	    cmdQueryBuilder.append(Constants.SPACE);
	});
    }

    private void appendDataType(StringBuilder cmdQueryBuilder, String type) {
	cmdQueryBuilder.append(Identifiers.TYPE.getIdentifier());
	cmdQueryBuilder.append(DataType.valueOf(type).getIdentifier());
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendOverrides(StringBuilder cmdQueryBuilder, DefaultOverrides overrides) {
	if (overrides.getQuantity() > 0) {
	    cmdQueryBuilder.append(Identifiers.QUANTITY.getIdentifier());
	    cmdQueryBuilder.append(overrides.getQuantity());
	    cmdQueryBuilder.append(Constants.SPACE);
	}
    }

    private void appendPrefix(StringBuilder cmdQueryBuilder, String prefix) {
	cmdQueryBuilder.append(Identifiers.PREFIX.getIdentifier());
	cmdQueryBuilder.append(prefix);
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendQuantity(StringBuilder cmdQueryBuilder, int quantity) {
	if (quantity > 0) {
	    cmdQueryBuilder.append(Identifiers.QUANTITY.getIdentifier());
	    cmdQueryBuilder.append(quantity);
	    cmdQueryBuilder.append(Constants.SPACE);
	}
    }

    private void appendQuerySeparator(StringBuilder cmdQueryBuilder) {
	cmdQueryBuilder.append(Identifiers.QUERY_SEPARATOR.getIdentifier());
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendRegex(StringBuilder cmdQueryBuilder, String regex) {
	StringJoiner sj = new StringJoiner(Constants.EMPTY_STRING, Constants.REGEX_EXPR_PREFIX,
		Constants.REGEX_EXPR_SUFFIX);
	sj.add(StringUtils.trimToEmpty(regex));
	cmdQueryBuilder.append(sj.toString());
	cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendSuffix(StringBuilder cmdQueryBuilder, String suffix) {
	cmdQueryBuilder.append(Identifiers.SUFFIX.getIdentifier());
	cmdQueryBuilder.append(suffix);
	cmdQueryBuilder.append(Constants.SPACE);

    }

    /**
     * Build and returns CLI query for single JSON file
     * 
     * @param jsonFile
     *            to build CLI query from
     * @return CLI query for the JSON File.
     */
    private String buildCLIQueryFrom(String jsonFile) {
	final long start = System.nanoTime();
	log.debug("Building query from JSON file {}", jsonFile);
	ObjectMapper mapper = new ObjectMapper();
	JsonQueryDetails jsonQueryDetails = null;
	try {
	    jsonQueryDetails = mapper.readValue(new File(jsonFile), JsonQueryDetails.class);
	    log.debug("Json Query Details set as => {}", jsonQueryDetails);
	} catch (Exception e) {
	    throw new QueryInterpretationException(e.getMessage());
	}

	final StringBuilder cmdQueryBuilder = new StringBuilder();

	try {
	    cmdQueryBuilder.append(Identifiers.DATA_GEN_QUERY_PREFIX.getIdentifier());
	    jsonQueryDetails.getDataDetails().stream().forEach(dataDetail -> {
		log.debug("Interpreting data generation details for {}", dataDetail);
		appendDataCollectorName(cmdQueryBuilder, dataDetail.getName());
		appendDataDetails(cmdQueryBuilder, dataDetail.getDetails());
		appendQuantity(cmdQueryBuilder, dataDetail.getQuantity());
		appendQuerySeparator(cmdQueryBuilder);
	    });
	    cmdQueryBuilder.append(Identifiers.DATA_GEN_QUERY_SUFFIX.getIdentifier());

	    if (jsonQueryDetails.isExportToCsv()) {
		log.debug("Export to CSV is True");
		cmdQueryBuilder.append(Constants.SPACE);
		cmdQueryBuilder.append(Identifiers.FILE.getIdentifier());
		cmdQueryBuilder.append(Constants.SPACE);

		cmdQueryBuilder.append(Identifiers.DATA_EXPORT_QUERY_PREFIX.getIdentifier());
		jsonQueryDetails.getExportDetails().stream().forEach(exportDetail -> {
		    log.debug("Interpreting export details for {}", exportDetail);
		    appendCSVFileName(cmdQueryBuilder, exportDetail.getCsvFile());
		    appendCSVColumnDetails(cmdQueryBuilder, exportDetail.getCsvDetails());
		    appendQuerySeparator(cmdQueryBuilder);
		});
		cmdQueryBuilder.append(Identifiers.DATA_EXPORT_QUERY_SUFFIX.getIdentifier());
	    }

	    // overrrides must be at end of the CMD query
	    if (null != jsonQueryDetails.getDefaults()) {
		log.debug("Encountered override flag");
		cmdQueryBuilder.append(Constants.SPACE);
		cmdQueryBuilder.append(Constants.GLOBAL_OVERRIDE);
		cmdQueryBuilder.append(Constants.SPACE);
		appendOverrides(cmdQueryBuilder, jsonQueryDetails.getDefaults());
	    }

	} catch (Exception e) {
	    throw new QueryInterpretationException(
		    "Error while Building CMD query from JSON file [" + jsonFile + "]. Error : " + e.getMessage());
	}

	log.debug("CLI Query formed for JSON {} is {}", jsonFile, cmdQueryBuilder);
	final long end = System.nanoTime();
	final double timeTaken = (end - start) / 1000000000.0;
	log.info("Time taken to parse JSON {} to CLI Query : {} seconds", jsonFile, timeTaken);
	return cmdQueryBuilder.toString();
    }

}
