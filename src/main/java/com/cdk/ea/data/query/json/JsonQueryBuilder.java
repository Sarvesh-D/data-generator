package com.cdk.ea.data.query.json;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.data.common.Builder;
import com.cdk.ea.data.core.DataType;
import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.core.Properties;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonQueryBuilder implements Builder<String>{

    @Override
    public String build(String... jsonFiles) {
	String jsonFile = jsonFiles[0];
	ObjectMapper mapper = new ObjectMapper();
	JsonQueryHolder jsonQueryHolder = null;
	try {
	    jsonQueryHolder = mapper.readValue(new File(jsonFile), JsonQueryHolder.class);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	
	final StringBuilder cmdQueryBuilder = new StringBuilder();
	try {
	    // TODO Re-factor to simplify
	    jsonQueryHolder.getDataTypes()
	    .stream()
	    .forEach(dataType -> {
		// get type identifier
		appendDataType(cmdQueryBuilder, dataType);
		
		// get property identifiers
		dataType.getProperties().stream()
		.forEach(property -> appendDataTypeProperties(cmdQueryBuilder, property));
		
		// get custom list values if present
		if(null != dataType.getList()) {
		    appendCustomListVals(cmdQueryBuilder, dataType.getList());
		}
		
		// get regex if present
		if(dataType.getType().equals(DataType.REGEX.getIdentifier().toString())) {
		    appendRegex(cmdQueryBuilder, dataType.getRegex());
		}
		
		// add query separator
		cmdQueryBuilder.append(Identifiers.QUERY_SEPARATOR.getIdentifier());
		cmdQueryBuilder.append(" ");
	    });
	    
	    // add query terminator
	    cmdQueryBuilder.append(Identifiers.QUERY_TERMINATOR.getIdentifier());
	    cmdQueryBuilder.append(" ");
	    
	    // check for any exporter
	    if(jsonQueryHolder.isExportToCsv()) {
		appendCSVFileDetails(cmdQueryBuilder, jsonQueryHolder.getCsvFile(), jsonQueryHolder.getCsvHeaders());
	    }
	    System.out.println(cmdQueryBuilder.toString());
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	return cmdQueryBuilder.toString();
    }

    private void appendCSVFileDetails(StringBuilder cmdQueryBuilder, String csvFile, List<String> csvHeaders) {
	cmdQueryBuilder.append(Identifiers.FILE.getIdentifier());
	cmdQueryBuilder.append(" ");
	cmdQueryBuilder.append(csvFile);
	cmdQueryBuilder.append(" ");
	String csvHeadersAsString = csvHeaders.stream()
            				.map(header -> Identifiers.CSV_HEADER.getIdentifier()+header)
            				.collect(Collectors.joining(" "));
	cmdQueryBuilder.append(csvHeadersAsString);
	cmdQueryBuilder.append(" ");
	
    }

    private void appendRegex(StringBuilder cmdQueryBuilder, String regex) {
	StringJoiner sj = new StringJoiner("", "{{", "}}");
	sj.add(StringUtils.trimToEmpty(regex));
	cmdQueryBuilder.append(sj.toString());
	cmdQueryBuilder.append(" ");
    }

    private void appendCustomListVals(StringBuilder cmdQueryBuilder, List<String> list) {
	String listVals = list.stream()
		.collect(Collectors.joining(",", "[[", "]]"));
	cmdQueryBuilder.append(listVals);
	cmdQueryBuilder.append(" ");
    }

    private void appendDataTypeProperties(StringBuilder cmdQueryBuilder, String property) {
	cmdQueryBuilder.append(Identifiers.PROPERTY.getIdentifier());
	cmdQueryBuilder.append(Properties.valueOf(property).getIdentifier());
	cmdQueryBuilder.append(" ");

    }

    private void appendDataType(StringBuilder cmdQueryBuilder, JsonQueryType dataType) {
	cmdQueryBuilder.append(Identifiers.TYPE.getIdentifier());
	cmdQueryBuilder.append(DataType.valueOf(dataType.getType()).getIdentifier());
	cmdQueryBuilder.append(" ");
    }

}
