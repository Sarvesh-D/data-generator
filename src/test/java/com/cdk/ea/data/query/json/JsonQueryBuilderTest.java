package com.cdk.ea.data.query.json;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.StartDataGeneration;
import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.generators.DataGenerator;

@RunWith(JUnit4.class)
public class JsonQueryBuilderTest {

    private String[] jsonFilePaths = new String[5];
    private List<String> createdFilePaths;

    @Before
    public void setUp() throws Exception {
	jsonFilePaths[0] = "src/test/resources/sample.json";
	createdFilePaths = Arrays.asList("sample_1.csv","sample_2.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }

    @After
    public void tearDown() throws Exception {
	jsonFilePaths = null;
    }

    @Test
    public final void testExecutionFromJson() {
	StartDataGeneration.main("json",jsonFilePaths[0],"-X");
    }
    
    @Test
    public final void testValidJsonWExport() {
	String cmdQuery_1 = new JsonQueryBuilder().build(jsonFilePaths[0]);
	assertTrue("CMD query cannot be null or blank", StringUtils.isNotBlank(cmdQuery_1));
	Collection<DataCollector> dataCollectedForQuery = DataGenerator.from(cmdQuery_1).generate();
	assertTrue("Four data collector must be present", dataCollectedForQuery.size() == 4);
	
	Path exportFile1 = Paths.get("sample_1.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile1);
	
	Path exportFile2 = Paths.get("sample_2.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile2);
    }

}
