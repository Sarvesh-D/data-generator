package com.ds.tools.data.generator.query.json;

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
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 *
 * @since 17-03-2017
 * @version 2.0
 */
public class CsvColumnDetailsDeserializer extends StdDeserializer<CsvColumnDetails> {

    private static final long serialVersionUID = 1L;

    protected CsvColumnDetailsDeserializer() {
        this(null);
    }

    protected CsvColumnDetailsDeserializer(final Class<?> vc) {
        super(vc);
    }

    @Override
    public CsvColumnDetails deserialize(final JsonParser p, final DeserializationContext ctxt) throws IOException, JsonProcessingException {

        final JsonNode node = p.getCodec()
                               .readTree(p);
        final String headerName = node.get("headerName")
                                      .asText();
        final JsonNode dataRefNode = node.get("dataRef");

        final Set<String> dataRefs = new HashSet<>();

        if (dataRefNode.isArray()) {
            dataRefNode.forEach(dataRef -> dataRefs.add(dataRef.asText()));
        } else {
            dataRefs.add(dataRefNode.asText());
        }

        return new CsvColumnDetails(headerName, dataRefs);
    }

}
