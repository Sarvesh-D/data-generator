package com.cdk.ea.data.query.json;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JsonQueryBuilderTest {

    private String[] jsonFilePaths = new String[5];

    @Before
    public void setUp() throws Exception {
	jsonFilePaths[0] = "src/test/resources/sample.json";
    }

    @After
    public void tearDown() throws Exception {
	jsonFilePaths = null;
    }

    @Test
    public final void testBuildQueryFromJson() {
	String cmdQuery_1 = new JsonQueryBuilder().build(jsonFilePaths[0]);
	assertTrue("CMD query cannot be null or blank", StringUtils.isNotBlank(cmdQuery_1));
    }

}
