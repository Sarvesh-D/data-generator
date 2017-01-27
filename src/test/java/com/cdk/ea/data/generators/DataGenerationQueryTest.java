package com.cdk.ea.data.generators;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.data.exception.InterpretationException;
import com.cdk.ea.data.exception.PropertiesInterpretationException;
import com.cdk.ea.data.exception.TypeInterpretationException;

@RunWith(JUnit4.class)
public class DataGenerationQueryTest {

    private List<String> createdFilePaths;

    @Before
    public void setUp() throws Exception {
	createdFilePaths = Arrays.asList("stringQueryWExport.csv", "sample_1.csv","sample_2.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }

    @Test
    public final void testValidQueryWoExport() {
	final String stringQueryWoExport = "(@RandomStrings :s -a -n -s l10 =100)";
	Collection<DataCollector> dataCollectedForQuery1 = DataGenerator.from(stringQueryWoExport).generate();
	assertTrue("One data collector must be present", dataCollectedForQuery1.size() == 1);
	dataCollectedForQuery1.stream().forEach(collector -> assertTrue("Quantity of data inside dataCollector should be 100", collector.getData().size() == 100));

    }

    @Test
    public final void testValidQueryWExport() {
	final String stringQueryWExport = "(@RandomStrings :s -a -n -s l10 =100) f <stringQueryWExport.csv _firstNames =RandomStrings>";
	Collection<DataCollector> dataCollectedForQuery = DataGenerator.from(stringQueryWExport).generate();
	assertTrue("One data collector must be present", dataCollectedForQuery.size() == 1);
	dataCollectedForQuery.stream().forEach(collector -> assertTrue("Quantity of data inside dataCollector should be 100", collector.getData().size() == 100));
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

    @Test(expected = InterpretationException.class)
    public final void testInvalidQueryForExport() {
	final String invalidQuery = "(@RandomStrings :s -a l10 =100) f <_Strings =RandomStrings>";
	DataGenerator.from(invalidQuery);
    }

}
