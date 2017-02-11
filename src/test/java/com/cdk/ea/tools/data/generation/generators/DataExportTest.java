package com.cdk.ea.tools.data.generation.generators;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generation.StartDataGeneration;
import com.opencsv.CSVReader;

/**
 * Test class for testing various Data Export queries
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 *
 * @since 11-02-2017
 * @version 1.0
 */
@RunWith(JUnit4.class)
public class DataExportTest {

    private List<String> createdFilePaths;

    @Before
    public void setUp() throws Exception {
	createdFilePaths = Arrays.asList("stringQueryWExport.csv", "sample_1.csv", "sample_2.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }

    @Test
    public final void testDataExportFromCmdQuery() {
	final String stringQueryWExport = "(@RandomStrings :s -a -n -s l10 =100) f <stringQueryWExport.csv _firstNames =RandomStrings>";
	Collection<DataCollector> dataCollectedForQuery = DataGenerator.from(stringQueryWExport).generate();
	assertTrue("One data collector must be present", dataCollectedForQuery.size() == 1);
	dataCollectedForQuery.stream()
		.forEach(collector -> assertTrue("Quantity of data inside dataCollector should be 100",
			collector.getData().size() == 100));

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
	StartDataGeneration.main(org.apache.commons.lang3.StringUtils.split("json src/test/resources/sample.json -X"));

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

}
