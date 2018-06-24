/**
 *
 */
package com.ds.tools.data.generator.rest;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ds.tools.data.generator.common.DataGeneratorUtils;
import com.ds.tools.data.generator.common.Utils;
import com.ds.tools.data.generator.core.DataGenerationStarter;
import com.ds.tools.data.generator.core.DataType;
import com.ds.tools.data.generator.core.Defaults;
import com.ds.tools.data.generator.core.Properties;
import com.ds.tools.data.generator.generators.DataCollector;
import com.ds.tools.data.generator.generators.DataGenerator;
import com.ds.tools.data.generator.query.json.DataGenerationDetails;
import com.ds.tools.data.generator.query.json.JsonQueryBuilder;
import com.ds.tools.data.generator.query.json.JsonQueryDetails;
import com.ds.tools.data.generator.rest.exception.BadRequestException;
import com.ds.tools.data.generator.types.TypeProperties;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 23 Jun 2018
 * @version 1.0
 */
@RestController
@RequestMapping("/data")
@Api("Data Generation/Export Controller")
@Slf4j
public class DataGeneratorRestController {

    @GetMapping("/types")
    @ApiOperation(value = "Get All Data Types supported by Data Generator")
    @ApiResponse(code = 200, message = "Successfully fetched all data types")
    public Set<String> getDataTypes() {
        return Arrays.stream(DataType.values())
                     .map(DataType::name)
                     .collect(Collectors.toSet());
    }

    @GetMapping("/properties")
    @ApiOperation(value = "Get All Data Properties supported by Data Generator")
    @ApiResponse(code = 200, message = "Successfully fetched all data properties")
    public Set<String> getDataProperties() {
        return Arrays.stream(Properties.values())
                     .map(Properties::name)
                     .collect(Collectors.toSet());
    }

    @GetMapping("/types/{type}/properties")
    @ApiOperation(value = "Get All Data Properties supported by given Data Type")
    @ApiResponses({ @ApiResponse(code = 200, message = "Successfully fetched properties for given data type"), @ApiResponse(code = 400, message = "Given Data Type is not supported") })
    public Set<String> getDataPropertiesByType(@PathVariable final String type) {
        try {
            final DataType dataType = DataType.valueOf(type);
            return Utils.getPropertiesFor(dataType)
                        .stream()
                        .map(TypeProperties::toString)
                        .collect(Collectors.toSet());
        } catch (final Exception e) {
            throw new BadRequestException(String.format("Unknown Type [%s]", type));
        }
    }

    @GetMapping("/types/export")
    @ApiOperation(value = "Get All data export formats supported by Data Generator")
    @ApiResponse(code = 200, message = "Successfully fetched all data export formats")
    public Set<String> getDataExportTypes() {
        return new HashSet<>(Arrays.asList("CSV"));
    }

    @PostMapping("/generate")
    @ApiOperation(value = "Generates data")
    @ApiResponse(code = 200, message = "Successfully Generated Data")
    public Collection<DataCollector> generateData(@RequestBody final List<DataGenerationDetails> dataGenerationDetails) {
        final long invalidQueries = dataGenerationDetails.stream()
                                                         .filter(dataGenerationDetail -> dataGenerationDetail.getQuantity() > Defaults.DEFAULT_SAMPLE_DATA_QTY)
                                                         .count();
        if (invalidQueries > 0) {
            log.warn("Queries with data quantity exceeding default sample data quantity of [{}] found. Data quantity will be set to [{}]", Defaults.DEFAULT_SAMPLE_DATA_QTY, Defaults.DEFAULT_SAMPLE_DATA_QTY);
        }
        dataGenerationDetails.stream()
                             .forEach(dataGenerationDetail -> dataGenerationDetail.setQuantity(Defaults.DEFAULT_SAMPLE_DATA_QTY));
        final StringBuilder dataGenerationQueryBuilder = new StringBuilder();
        JsonQueryBuilder.getInstance()
                        .appendDataGenerationDetails(dataGenerationQueryBuilder, dataGenerationDetails);
        return DataGenerator.from(DataGeneratorUtils.getDataGenQueries(dataGenerationQueryBuilder.toString()))
                            .generate();
    }

    @PostMapping("/export")
    @ApiOperation(value = "Exports data")
    @ApiResponse(code = 200, message = "Successfully Exported Data")
    public HttpEntity<FileLocations> exportData(@RequestBody final JsonQueryDetails jsonQueryDetails) {
        final long defaultQty = jsonQueryDetails.getDefaults()
                                                .getQuantity();
        if (defaultQty > Defaults.MAX_DATA_EXPORT_QTY) {
            log.warn("Data generation Default Quantity [{}] exceeds maximum data generation quantity of [{}]. Setting default quantity as [{}]", defaultQty, Defaults.MAX_DATA_EXPORT_QTY, Defaults.MAX_DATA_EXPORT_QTY);
            jsonQueryDetails.getDefaults()
                            .setQuantity(Defaults.MAX_DATA_EXPORT_QTY);
        }
        jsonQueryDetails.getDataDetails()
                        .stream()
                        .forEach(dataDetail -> {
                            if (dataDetail.getQuantity() > Defaults.MAX_DATA_EXPORT_QTY) {
                                log.warn("Data Generator [{}] exceeds maximum data generation quantity of [{}]. Setting quantity for data generator [{}] as [{}]", dataDetail.getName(), Defaults.MAX_DATA_EXPORT_QTY, dataDetail.getName(), Defaults.MAX_DATA_EXPORT_QTY);
                                dataDetail.setQuantity(Defaults.MAX_DATA_EXPORT_QTY);
                            }
                        });
        jsonQueryDetails.setExportToCsv(true);
        jsonQueryDetails.getExportDetails()
                        .forEach(exportDetail -> exportDetail.setCsvFile(DataGeneratorRestApplication.TEMP_DIR + "/" + exportDetail.getCsvFile()));

        DataGenerationStarter.invokeDataGeneratorFor(JsonQueryBuilder.getInstance()
                                                                     .buildCLIQueryFrom(jsonQueryDetails));

        log.info("Data Exported");
        final FileLocations fileLocations = new FileLocations();
        jsonQueryDetails.getExportDetails()
                        .forEach(exportDetail -> fileLocations.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(DataGeneratorRestController.class)
                                                                                                                     .getExportedData(exportDetail.getCsvFile()))
                                                                                        .withRel("files")));
        return new ResponseEntity<>(fileLocations, HttpStatus.OK);
    }

    @GetMapping("/export")
    @ApiOperation(value = "Download Data File")
    @ApiResponse(code = 200, message = "Successfully Downloaded Data File")
    public ResponseEntity<Resource> getExportedData(@RequestParam final String fileName) {
        log.info("Starting to download file [{}]", fileName);
        final Resource resource = new FileSystemResource(fileName);
        return ResponseEntity.ok()
                             .contentType(MediaType.APPLICATION_OCTET_STREAM)
                             .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                             .body(resource);
    }

}
