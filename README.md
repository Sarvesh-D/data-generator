# Data Generation Tool

## Synopsis
### Tool for generating and exporting random data. Mostly used for generating large amount of random data for performing load/regression tests.


## Features

 - Generate random Strings, Numbers.
 - Generate random data from pre-defined list.
 - Generate random Strings matching given regex pattern.
 - Generate random Strings with specified prefix/suffix.
 - Export generated data to CSV file(s).



## Installation
The data-generator has two modules viz. data-generator-core (to be used as dependency) data-generator-runnable (to be used as standalone jar).

Download the runnable jar and start generating and exporting data (see CLI usage and JSON usage):


| data-generator version | download link |
|------------------------|---------------|
| 1.0.0-RELEASE          | [jar]()            |


Add data-generator dependency to your existing maven project:

    <dependency>
        <groupId>com.ds.tools</groupId>
        <artifactId>data-generator-core</artifactId>
        <version>1.0.0-RELEASE</version>
    </dependency>



## Usage
Download the jar and goto the jar directory. Open Terminal/CMD and execute below:

    java -jar <name of jar file> --help



#### Using JSON file
The tool is designed for simple usage via JSON file. The JSON file tells the tool how the data should be generated and exported. See JSON structure [here]().

    java -jar <name of jar file> json /path/to/json/file



#### Using CMD line Queries
This option is available for advanced users or for the users who want to quickly see the tool in action. The arguments passed to the tool from CMD line are used to form queries which are then executed to generate and export data. There are two categories of queries viz. DataGenerationQuery and DataExportQuery.



##### Query Format:

    (DataGenQuery_1 | DataGenQuery_2 | ...) f <DataExportQuery_1 | DataExportQuery_2 | ...>



##### Data Generation Query Format

    @DataCollectorName :DataType -DataProperty1 -DataProperty2 lDataLength =DataQuantity



##### Data Generation Query Examples:

    @RandomAlphaStrings :s -a l10 =100
    @RandomAlphaNumericStrings :s -a -n l10 =100
    @RandomStringsWithPrefix :s -a Pbegin l10 =100
    @RandomStringsWithSuffix :s -a Send l10 =100
    @RandomValueFromCustomList :l -u [[Value1,Value2,Value3]] =20



##### Data Export Query Format:

    PathToCsvFile _headerName1 =dataCollectorName1 _headerName2 =dataCollectorName2



##### Data Generation Query Examples:

    /Users/any/randomData1.csv _FirstNames =RandomAlphaStrings _ListValues =RandomValueFromCustomList

    /Users/any/randomData2.csv _FirstNames =RandomAlphaStrings _LastNames =RandomAlphaStrings



## Links

 1. [Data-Generator Wiki]()
 2. [Examples]()
 3. [JSON Format]()
 4. [Code Repository]()
 5. [JIRA/Issues]()
