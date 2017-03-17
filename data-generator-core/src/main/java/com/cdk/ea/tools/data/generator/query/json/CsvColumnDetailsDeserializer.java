package com.cdk.ea.tools.data.generator.query.json;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

/**
 * Custom Deserializer to support backward compatibility with multiple dataRefs
 * feature added in release 2.0
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 *
 * @since 17-03-2017
 * @version 2.0
 */
public class CsvColumnDetailsDeserializer extends StdDeserializer<CsvColumnDetails> {

    private static final long serialVersionUID = 1L;

    protected CsvColumnDetailsDeserializer() {
	this(null);
    }

    protected CsvColumnDetailsDeserializer(Class<?> vc) {
	super(vc);
    }

    @Override
    public CsvColumnDetails deserialize(JsonParser p, DeserializationContext ctxt)
	    throws IOException, JsonProcessingException {

	JsonNode node = p.getCodec().readTree(p);
	String headerName = node.get("headerName").asText();
	JsonNode dataRefNode = node.get("dataRef");

	Set<String> dataRefs = new HashSet<>();

	if (dataRefNode.isArray())
	    dataRefNode.forEach(dataRef -> dataRefs.add(dataRef.asText()));
	else
	    dataRefs.add(dataRefNode.asText());

	return new CsvColumnDetails(headerName, dataRefs);
    }

}
