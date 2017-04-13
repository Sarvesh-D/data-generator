package com.cdk.ea.tools.data.generator.generators;

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
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generator.common.DataGeneratorUtils;
import com.cdk.ea.tools.data.generator.core.Constants;
import com.cdk.ea.tools.data.generator.core.DataGenerationStarter;
import com.cdk.ea.tools.data.generator.exporters.DataExporter;
import com.cdk.ea.tools.data.generator.generators.DataCollector;
import com.cdk.ea.tools.data.generator.generators.DataGenerator;
import com.opencsv.CSVReader;

/**
 * Test class for testing various Data Export queries
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class DataExportTest {

    private static List<String> createdFilePaths;

    @BeforeClass
    public static void setUp() throws Exception {
	createdFilePaths = Arrays.asList("stringQueryWExport.csv", "stringQueryWExport_2.csv", "sample_1.csv",
		"sample_2.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }

    @AfterClass
    public static void tearDown() throws Exception {
	createdFilePaths = null;
    }

    @Test
    public final void testDataExportFromCmdQuery() {
	final String stringQueryWExport = "(@RandomStrings :s -a -n -s l10 =100) f <stringQueryWExport.csv _firstNames =RandomStrings>";
	Collection<DataCollector> dataCollectedForQuery = DataGenerator
		.from(DataGeneratorUtils.getDataGenQueries(stringQueryWExport)).generate();
	assertTrue("One data collector must be present", dataCollectedForQuery.size() == 1);
	dataCollectedForQuery.stream()
		.forEach(collector -> assertTrue("Quantity of data inside dataCollector should be 100",
			collector.getData().size() == 100));

	DataExporter.from(DataGeneratorUtils.getDataExportQueries(stringQueryWExport)).export(dataCollectedForQuery);
	CSVReader reader = null;
	try {
	    reader = new CSVReader(new FileReader("stringQueryWExport.csv"));
	    assertTrue("Quantity of data in csv file should be 101 including csv header",
		    reader.readAll().size() == 101);
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

    @Test
    public final void testDataExportFromJson() {
	DataGenerationStarter.start("json src/test/resources/sample.json -X");

	CSVReader reader = null;
	try {
	    reader = new CSVReader(new FileReader("sample_1.csv"));
	    int lines = reader.readAll().size();
	    assertTrue("Number of lines in sample_1.csv file should be 201 including csv header", lines == 201);
	    reader = new CSVReader(new FileReader("sample_2.csv"));
	    lines = reader.readAll().size();
	    assertTrue("Number of lines in sample_2.csv file should be 11 including csv header", lines == 11);
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

    @Test
    public final void testMultipleValidQueriesWithExport() {
	final String stringQueryWExport = "(@RandomStrings :s -a -n -s l10) f <stringQueryWExport.csv _firstNames =RandomStrings> --o =1000";
	final String stringQueryWoExport = "(@RandomStrings :s -a -n -s l10) f <stringQueryWExport_2.csv _firstNames =RandomStrings> --o =500";
	final String multipleValidQueris = new StringJoiner(Constants.CLI_QUERY_SEPARATOR).add(stringQueryWExport)
		.add(stringQueryWoExport).toString();
	DataGenerationStarter.start(multipleValidQueris);

	Path exportFile1 = Paths.get("stringQueryWExport.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile1);

	Path exportFile2 = Paths.get("stringQueryWExport_2.csv");
	assertNotNull("Path to where file was exported does not exists", exportFile2);

	CSVReader reader = null;
	try {
	    reader = new CSVReader(new FileReader("stringQueryWExport.csv"));
	    int lines = reader.readAll().size();
	    assertTrue("Quantity of data in csv file should be 1001 including csv header", lines == 1001);

	    reader = new CSVReader(new FileReader("stringQueryWExport_2.csv"));
	    lines = reader.readAll().size();
	    assertTrue("Quantity of data in csv file should be 501 including csv header", lines == 501);
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
