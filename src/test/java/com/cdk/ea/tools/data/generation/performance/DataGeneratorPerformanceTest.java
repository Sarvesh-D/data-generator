package com.cdk.ea.tools.data.generation.performance;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.cdk.ea.tools.data.generation.StartDataGeneration;
import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.DataType;
import com.cdk.ea.tools.data.generation.types.ListType.ListTypeBuilder;

import lombok.extern.java.Log;

@RunWith(JUnit4.class)
@Log
public class DataGeneratorPerformanceTest {

    private static List<String> createdFilePaths;

    private long start;
    private long end;
    private double timeTaken;

    @BeforeClass
    public static void setUp() throws Exception {
	createdFilePaths = Arrays.asList("sample_1.csv");
	createdFilePaths.stream().forEach(filePath -> new File(filePath).deleteOnExit());
    }

    @AfterClass
    public static void tearDown() throws Exception {
	createdFilePaths = null;
    }

    @Before
    public void startTime() throws Exception {
	start = System.nanoTime();
    }

    @After
    public void stopTime() throws Exception {
	end = System.nanoTime();
	timeTaken = (end - start) / 1000000000.0;
	log.info(String.format("Time Taken : %f seconds", timeTaken));
    }

    @Test
    public final void testDataGeneratorPerformanceCLI() {
	final String CLIQuery = "(@Strings :s -a -n -s l10 Ppre Ssuf | @ListVals :l -u l0 [[Mr.,Miss]] | @Numbers :n -i l0 | @customRegex :r -r l6 {{[a-zA-Z]+[@][0-9]+}} | ) --o =10000 ";
	StartDataGeneration.main(CLIQuery);
    }

    @Test
    public final void testDataGeneratorPerformanceJSON() {
	StringJoiner jsonQuery = new StringJoiner(Constants.SPACE).add(Constants.JSON);
	jsonQuery.add("src/test/resources/perfWithoutExport.json");
	StartDataGeneration.main(StringUtils.split(jsonQuery.toString()));
    }

    @Test
    public final void testDataGeneratorPerformanceCLIWithExport() {
	final String CLIQuery = "(@Strings :s -a -n -s l10 Ppre Ssuf | @ListVals :l -u l0 [[Mr.,Miss]] | @Numbers :n -i l0 | @customRegex :r -r l6 {{[a-zA-Z]+[@][0-9]+}} | ) f <sample_1.csv _NamePrefixs =ListVals _AnyNumber =Numbers _FunnyTexts =customRegex _FunnyStrings =Strings | > --o =10000 ";
	StartDataGeneration.main(CLIQuery);
    }

    @Test
    public final void testDataGeneratorPerformanceJSONWithExport() {
	StringJoiner jsonQuery = new StringJoiner(Constants.SPACE).add(Constants.JSON);
	jsonQuery.add("src/test/resources/perfWithExport.json");
	StartDataGeneration.main(StringUtils.split(jsonQuery.toString()));
    }

}
