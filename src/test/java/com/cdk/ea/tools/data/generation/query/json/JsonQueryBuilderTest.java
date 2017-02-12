package com.cdk.ea.tools.data.generation.query.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generation.StartDataGeneration;
import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.exception.DataGeneratorException;
import com.cdk.ea.tools.data.generation.exception.TypeInterpretationException;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.generators.DataGenerator;

/**
 * Test Class for testing {@link JsonQueryBuilder}
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class JsonQueryBuilderTest {

    private static List<String> jsonFilePaths;
    private static List<String> createdFilePaths;

    @BeforeClass
    public static void setUp() throws Exception {
	jsonFilePaths = Arrays.asList("src/test/resources/sample.json", "src/test/resources/sample_2.json");
	createdFilePaths = Arrays.asList("sample_1.csv", "sample_2.csv", "sample_3.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }

    @AfterClass
    public static void tearDown() throws Exception {
	jsonFilePaths = null;
	createdFilePaths = null;
    }

    @Test
    public final void testExecutionFromJson() {
	StringJoiner jsonQuery = new StringJoiner(Constants.SPACE).add(Constants.JSON);
	jsonQuery.add("src/test/resources/sample.json");
	jsonQuery.add(Constants.DEBUG_ENABLED);
	StartDataGeneration.main(StringUtils.split(jsonQuery.toString()));
    }

    @Test
    public final void testValidJsonWExport() {
	String cmdQuery_1 = new JsonQueryBuilder().build("src/test/resources/sample.json");
	assertTrue("CMD query cannot be null or blank", StringUtils.isNotBlank(cmdQuery_1));
	Collection<DataCollector> dataCollectedForQuery = DataGenerator.from(cmdQuery_1).generate();
	assertTrue("Four data collector must be present", dataCollectedForQuery.size() == 4);

	Path exportFile1 = Paths.get("sample_1.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile1);

	Path exportFile2 = Paths.get("sample_2.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile2);
    }
    
    @Test(expected = DataGeneratorException.class)
    public final void testInvalidJson() {
	new JsonQueryBuilder().build("src/test/resources/invalid.json");
    }
    
    @Test
    public final void testExecutionFromMultipleJson() {
	StringJoiner jsonQuery = new StringJoiner(Constants.SPACE).add(Constants.JSON);
	jsonFilePaths.stream().forEach(jsonQuery::add);
	jsonQuery.add(Constants.DEBUG_ENABLED);
	StartDataGeneration.main(StringUtils.split(jsonQuery.toString()));
	
	Path exportFile1 = Paths.get("sample_1.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile1);

	Path exportFile2 = Paths.get("sample_2.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile2);
	
	Path exportFile3 = Paths.get("sample_3.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile3);

    }

}
