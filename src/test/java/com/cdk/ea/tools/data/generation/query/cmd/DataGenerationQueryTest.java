package com.cdk.ea.tools.data.generation.query.cmd;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generation.StartDataGeneration;
import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.exception.DataExportException;
import com.cdk.ea.tools.data.generation.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generation.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generation.exception.TypeInterpretationException;
import com.cdk.ea.tools.data.generation.generators.DataCollector;
import com.cdk.ea.tools.data.generation.generators.DataGenerator;
import com.opencsv.CSVReader;

/**
 * Test class for testing data generation queries
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class DataGenerationQueryTest {

    private static List<String> createdFilePaths;

    @BeforeClass
    public static void setUp() throws Exception {
	createdFilePaths = Arrays.asList("stringQueryWExport.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }
    
    @AfterClass
    public static void tearDown() throws Exception {
	createdFilePaths = null;
    }

    @Test
    public final void testDisplayHelp() {
	StartDataGeneration.main("--help");
    }

    @Test
    public final void testExecutionFromCmd() {
	final String stringQueryWExport = "(@RandomStrings :s -a -n -s l10 =100) f <stringQueryWExport.csv _firstNames =RandomStrings> -X";
	StartDataGeneration.main(stringQueryWExport);
    }

    @Test(expected = QueryInterpretationException.class)
    public final void testInvalidQuery() {
	final String invalidQuery = "(@RandomStrings :s -a l10 =100) f <>";
	DataGenerator.from(invalidQuery);
    }

    @Test(expected = DataExportException.class)
    public final void testInvalidQueryForExport() {
	final String invalidQuery = "(@RandomStrings :s -a l10 =100) f <_Strings =RandomStrings>";
	DataGenerator.from(invalidQuery);
    }

    @Test(expected = PropertiesInterpretationException.class)
    public final void testInvalidQueryForProps() {
	final String invalidQuery_1 = "(@RandomStrings :s -i l10 =100)";
	final String invalidQuery_2 = "(@RandomStrings :i -s l10 =100)";
	DataGenerator.from(invalidQuery_1);
	DataGenerator.from(invalidQuery_2);
    }

    @Test(expected = TypeInterpretationException.class)
    public final void testInvalidQueryForType() {
	final String invalidQuery = "(@RandomStrings :x -i l10 =100)";
	DataGenerator.from(invalidQuery);
    }

    @Test
    public final void testValidQueryWExport() {
	final String stringQueryWExport = "(@RandomStrings :s -a -n -s l10 =100) f <stringQueryWExport.csv _firstNames =RandomStrings>";
	Collection<DataCollector> dataCollectedForQuery = DataGenerator.from(stringQueryWExport).generate();
	assertTrue("One data collector must be present", dataCollectedForQuery.size() == 1);
	dataCollectedForQuery.stream()
		.forEach(collector -> assertTrue("Quantity of data inside dataCollector should be 100",
			collector.getData().size() == 100));

	Path exportFile1 = Paths.get("stringQueryWExport.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile1);
    }

    @Test
    public final void testValidQueryWoExport() {
	final String stringQueryWoExport = "(@RandomStrings :s -a -n -s l10 =100)";
	Collection<DataCollector> dataCollectedForQuery1 = DataGenerator.from(stringQueryWoExport).generate();
	assertTrue("One data collector must be present", dataCollectedForQuery1.size() == 1);
	dataCollectedForQuery1.stream()
		.forEach(collector -> assertTrue("Quantity of data inside dataCollector should be 100",
			collector.getData().size() == 100));

    }
    
    @Test
    public final void testMultipleInvalidQueries() {
	final String invalidQuery_1 = "(@RandomStrings :s -a l10 =100) f <>";
	final String invalidQuery_2 = "(@RandomStrings :x -i l10 =100)";
	final String multipleInvalidQueris = new StringJoiner(Constants.CLI_QUERY_SEPARATOR).add(invalidQuery_1)
		.add(invalidQuery_2).toString();
	StartDataGeneration.main(multipleInvalidQueris);
    }
    
    @Test
    public final void testMultipleQueries() {
	final String invalidQuery = "(@RandomStrings :x -i l10 =100)";
	final String validQuery = "(@RandomStrings :s -a -n -s l10) f <stringQueryWExport.csv _firstNames =RandomStrings> --o =999";
	final String multipleQueris = new StringJoiner(Constants.CLI_QUERY_SEPARATOR).add(invalidQuery)
		.add(validQuery).toString();
	StartDataGeneration.main(multipleQueris);
	
	Path exportFile1 = Paths.get("stringQueryWExport.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile1);
	
	CSVReader reader = null;
	try {
	    reader = new CSVReader(new FileReader("stringQueryWExport.csv"));
	    assertTrue("Quantity of data in csv file should be 1000 including csv header",
		    reader.readAll().size() == 1000);
	} catch (IOException e) {
	    fail(e.getMessage());
	} finally {
	    try {
		reader.close();
	    } catch (IOException e) {
		fail(e.getMessage());
	    }
	}
	
    }

}
