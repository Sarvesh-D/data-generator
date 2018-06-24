package com.ds.tools.data.generator.query.json;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ds.tools.data.generator.common.Builder;
import com.ds.tools.data.generator.core.Constants;
import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Identifiers;
import com.ds.tools.data.generator.core.Properties;
import com.ds.tools.data.generator.exception.QueryInterpretationException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Builder Class responsible for converting the JSON file(s) to CLI query(s)
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
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
    public String build(final List<String> jsonFiles) {
        final long start = System.nanoTime();
        final List<String> cliQueries = new ArrayList<>();
        jsonFiles.forEach(jsonFile -> cliQueries.add(buildCLIQueryFrom(jsonFile)));
        final String finalCLIQuery = cliQueries.stream()
                                               .collect(Collectors.joining(Constants.CLI_QUERY_SEPARATOR));
        final long end = System.nanoTime();
        final double timeTaken = (end - start) / 1000000000.0;
        log.info("Time taken to parse all JSONs to CLI Query : {} seconds", timeTaken);
        return finalCLIQuery;
    }

    private void appendCSVColumnDetails(final StringBuilder cmdQueryBuilder, final List<CsvColumnDetails> csvColumnDetails) {
        csvColumnDetails.stream()
                        .forEach(csvColumnDetail -> {
                            cmdQueryBuilder.append(Identifiers.CSV_HEADER_PREFIX.getIdentifier());
                            cmdQueryBuilder.append(csvColumnDetail.getHeaderName());
                            cmdQueryBuilder.append(Constants.SPACE);
                            cmdQueryBuilder.append(Identifiers.CSV_COL_DATA_REF.getIdentifier());
                            cmdQueryBuilder.append(csvColumnDetail.getDataRef()
                                                                  .stream()
                                                                  .collect(Collectors.joining(Constants.COMMA)));
                            cmdQueryBuilder.append(Constants.SPACE);
                        });
    }

    private void appendCSVFileName(final StringBuilder cmdQueryBuilder, final String csvFileName) {
        cmdQueryBuilder.append(csvFileName);
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendCustomListVals(final StringBuilder cmdQueryBuilder, final List<String> list) {
        final String listVals = list.stream()
                                    .collect(Collectors.joining(Constants.COMMA, Constants.CUSTOM_LIST_VALS_PREFIX, Constants.CUSTOM_LIST_VALS_SUFFIX));
        cmdQueryBuilder.append(listVals);
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendDataCollectorName(final StringBuilder cmdQueryBuilder, final String collectorName) {
        cmdQueryBuilder.append(Identifiers.DATA_COLLECTOR_PREFIX.getIdentifier());
        cmdQueryBuilder.append(collectorName);
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendDataDetails(final StringBuilder cmdQueryBuilder, final DataDetails dataDetails) {
        // add data type
        appendDataType(cmdQueryBuilder, dataDetails.getType());

        // add data properties
        appendDataProperties(cmdQueryBuilder, dataDetails.getProperties());

        // add data length
        appendDataLength(cmdQueryBuilder, dataDetails.getLength());

        // get custom list values if present
        if (null != dataDetails.getList() && !dataDetails.getList()
                                                         .isEmpty()) {
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

        // get Locale if present
        if (StringUtils.isNotBlank(dataDetails.getLocale())) {
            appendLocale(cmdQueryBuilder, dataDetails.getLocale());
        }
    }

    private void appendDataLength(final StringBuilder cmdQueryBuilder, final int quantity) {
        cmdQueryBuilder.append(Identifiers.LENGTH.getIdentifier());
        cmdQueryBuilder.append(quantity);
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendDataProperties(final StringBuilder cmdQueryBuilder, final Set<String> properties) {
        properties.stream()
                  .forEach(property -> {
                      Object propertyIdentifier = null;
                      try {
                          propertyIdentifier = Properties.valueOf(property)
                                                         .getIdentifier();
                      } catch (final Exception e) {
                          log.warn("Unable to get property identifier of property [{}] from ENUM Properties. Possible cause: Property [{}] belongs to Faker Properties", property, property);
                          propertyIdentifier = property;
                      } finally {
                          cmdQueryBuilder.append(Identifiers.PROPERTY.getIdentifier());
                          cmdQueryBuilder.append(propertyIdentifier);
                          cmdQueryBuilder.append(Constants.SPACE);
                      }
                  });
    }

    private void appendDataType(final StringBuilder cmdQueryBuilder, final String type) {
        cmdQueryBuilder.append(Identifiers.TYPE.getIdentifier());
        cmdQueryBuilder.append(DataType.valueOf(type)
                                       .getIdentifier());
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendLocale(final StringBuilder cmdQueryBuilder, final String locale) {
        cmdQueryBuilder.append(Identifiers.LOCALE.getIdentifier());
        cmdQueryBuilder.append(locale);
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendOverrides(final StringBuilder cmdQueryBuilder, final DefaultOverrides overrides) {
        if (overrides.getQuantity() > 0) {
            cmdQueryBuilder.append(Identifiers.QUANTITY.getIdentifier());
            cmdQueryBuilder.append(overrides.getQuantity());
            cmdQueryBuilder.append(Constants.SPACE);
        }
    }

    private void appendPrefix(final StringBuilder cmdQueryBuilder, final String prefix) {
        cmdQueryBuilder.append(Identifiers.PREFIX.getIdentifier());
        cmdQueryBuilder.append(prefix);
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendQuantity(final StringBuilder cmdQueryBuilder, final int quantity) {
        if (quantity > 0) {
            cmdQueryBuilder.append(Identifiers.QUANTITY.getIdentifier());
            cmdQueryBuilder.append(quantity);
            cmdQueryBuilder.append(Constants.SPACE);
        }
    }

    private void appendQuerySeparator(final StringBuilder cmdQueryBuilder) {
        cmdQueryBuilder.append(Identifiers.QUERY_SEPARATOR.getIdentifier());
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendRegex(final StringBuilder cmdQueryBuilder, final String regex) {
        final StringJoiner sj = new StringJoiner(Constants.EMPTY_STRING, Constants.REGEX_EXPR_PREFIX, Constants.REGEX_EXPR_SUFFIX);
        sj.add(StringUtils.trimToEmpty(regex));
        cmdQueryBuilder.append(sj.toString());
        cmdQueryBuilder.append(Constants.SPACE);
    }

    private void appendSuffix(final StringBuilder cmdQueryBuilder, final String suffix) {
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
    private String buildCLIQueryFrom(final String jsonFile) {
        final long start = System.nanoTime();
        log.debug("Building query from JSON file {}", jsonFile);
        final ObjectMapper mapper = new ObjectMapper();
        JsonQueryDetails jsonQueryDetails = null;
        try {
            jsonQueryDetails = mapper.readValue(new File(jsonFile), JsonQueryDetails.class);
            log.debug("Json Query Details set as => {}", jsonQueryDetails);
        } catch (final Exception e) {
            throw new QueryInterpretationException(e.getMessage());
        }

        try {
            final String cliQuery = buildCLIQueryFrom(jsonQueryDetails);
            log.debug("CLI Query formed for JSON {} is {}", jsonFile, cliQuery);
            final long end = System.nanoTime();
            final double timeTaken = (end - start) / 1000000000.0;
            log.info("Time taken to parse JSON {} to CLI Query : {} seconds", jsonFile, timeTaken);
            return cliQuery;
        } catch (final Exception e) {
            throw new QueryInterpretationException("Error while Building CMD query from JSON file [" + jsonFile + "]. Error : " + e.getMessage());
        }

    }

    /**
     * Build and returns CLI query for {@link JsonQueryDetails}
     *
     * @param jsonQueryDetails
     * @return CLI query
     */
    public String buildCLIQueryFrom(final JsonQueryDetails jsonQueryDetails) {
        final StringBuilder cmdQueryBuilder = new StringBuilder();
        appendDataGenerationDetails(cmdQueryBuilder, jsonQueryDetails.getDataDetails());

        if (jsonQueryDetails.isExportToCsv()) {
            appendDataExportDetails(cmdQueryBuilder, jsonQueryDetails.getExportDetails());
        }

        // overrrides must be at end of the CMD query
        if (null != jsonQueryDetails.getDefaults()) {
            log.debug("Encountered override flag");
            cmdQueryBuilder.append(Constants.SPACE);
            cmdQueryBuilder.append(Constants.GLOBAL_OVERRIDE);
            cmdQueryBuilder.append(Constants.SPACE);
            appendOverrides(cmdQueryBuilder, jsonQueryDetails.getDefaults());
        }

        return cmdQueryBuilder.toString();
    }

    /**
     * @param cmdQueryBuilder
     * @param exportDetails
     */
    public void appendDataExportDetails(final StringBuilder cmdQueryBuilder, final List<DataExportDetails> exportDetails) {
        log.debug("Export to CSV is True");
        cmdQueryBuilder.append(Constants.SPACE);
        cmdQueryBuilder.append(Identifiers.FILE.getIdentifier());
        cmdQueryBuilder.append(Constants.SPACE);

        cmdQueryBuilder.append(Identifiers.DATA_EXPORT_QUERY_PREFIX.getIdentifier());
        exportDetails.stream()
                     .forEach(exportDetail -> {
                         log.debug("Interpreting export details for {}", exportDetail);
                         appendCSVFileName(cmdQueryBuilder, exportDetail.getCsvFile());
                         appendCSVColumnDetails(cmdQueryBuilder, exportDetail.getCsvDetails());
                         appendQuerySeparator(cmdQueryBuilder);
                     });
        cmdQueryBuilder.append(Identifiers.DATA_EXPORT_QUERY_SUFFIX.getIdentifier());
    }

    /**
     * @param cmdQueryBuilder
     * @param dataDetails
     */
    public void appendDataGenerationDetails(final StringBuilder cmdQueryBuilder, final List<DataGenerationDetails> dataDetails) {
        cmdQueryBuilder.append(Identifiers.DATA_GEN_QUERY_PREFIX.getIdentifier());
        dataDetails.stream()
                   .forEach(dataDetail -> {
                       log.debug("Interpreting data generation details for {}", dataDetail);
                       appendDataCollectorName(cmdQueryBuilder, dataDetail.getName());
                       appendDataDetails(cmdQueryBuilder, dataDetail.getDetails());
                       appendQuantity(cmdQueryBuilder, dataDetail.getQuantity());
                       appendQuerySeparator(cmdQueryBuilder);
                   });
        cmdQueryBuilder.append(Identifiers.DATA_GEN_QUERY_SUFFIX.getIdentifier());

    }

}
