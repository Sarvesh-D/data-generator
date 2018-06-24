/**
 *
 */
package com.ds.tools.data.generator.rest;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 24 Jun 2018
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DataGeneratorRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#getDataTypes()}.
     *
     * @throws Exception
     */
    @Test
    public void testGetDataTypes() throws Exception {
        this.mockMvc.perform(get("/data/types"))
                    .andExpect(status().isOk());
    }

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#getDataProperties()}.
     *
     * @throws Exception
     */
    @Test
    public void testGetDataProperties() throws Exception {
        this.mockMvc.perform(get("/data/properties"))
                    .andExpect(status().isOk());
    }

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#getDataPropertiesByType(java.lang.String)}.
     *
     * @throws Exception
     */
    @Test
    public void testGetDataPropertiesByType() throws Exception {
        this.mockMvc.perform(get("/data/types/STRING/properties"))
                    .andExpect(status().isOk());
        this.mockMvc.perform(get("/data/types/DUMMY/properties"))
                    .andExpect(status().isBadRequest());
    }

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#getDataExportTypes()}.
     *
     * @throws Exception
     */
    @Test
    public void testGetDataExportTypes() throws Exception {
        this.mockMvc.perform(get("/data/types/export"))
                    .andExpect(status().isOk());
    }

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#generateData(java.util.List)}.
     *
     * @throws Exception
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testGenerateData() throws Exception {
        this.mockMvc.perform(post("/data/generate").contentType(MediaType.APPLICATION_JSON)
                                                   .content(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("data_generation_request.json")
                                                                                                    .toURI()))))
                    .andExpect(status().isOk());
    }

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#exportData(com.ds.tools.data.generator.query.json.JsonQueryDetails)}.
     *
     * @throws Exception
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testExportData() throws Exception {
        this.mockMvc.perform(post("/data/export").contentType(MediaType.APPLICATION_JSON)
                                                 .content(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("sample.json")
                                                                                                  .toURI()))))
                    .andExpect(status().isOk());
    }

    /**
     * Test method for
     * {@link com.ds.tools.data.generator.rest.DataGeneratorRestController#getExportedData(java.lang.String)}.
     *
     * @throws Exception
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testGetExportedData() throws Exception {
        final MvcResult mvcResult = mockMvc.perform(post("/data/export").contentType(MediaType.APPLICATION_JSON)
                                                                        .content(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("sample.json")
                                                                                                                         .toURI()))))
                                           .andReturn();
        final JsonNode response = new ObjectMapper().readTree(mvcResult.getResponse()
                                                                       .getContentAsString());

        final List<String> fileLocations = new ArrayList<>();
        response.path("_links")
                .path("files")
                .elements()
                .forEachRemaining(file -> fileLocations.add(file.get("href")
                                                                .asText()));

        assertTrue("Exactly 2 files should be exported", fileLocations.size() == 2);

        fileLocations.forEach(file -> {
            try {
                this.mockMvc.perform(get(file))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
                            .andExpect(header().exists(HttpHeaders.CONTENT_DISPOSITION));
            } catch (final Exception e) {
                fail("Unable to download file containing export data", e);
            }
        });

    }

}
