package com.cdk.ea.data.exporters;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cdk.ea.data.generators.DataCollector;
import com.cdk.ea.data.query.json.CsvColumnDetails;
import com.opencsv.CSVWriter;

public class CSVFileExporter implements FileExporter {
    
    private String filePath;
    
    // map with csvHeaderName as key and dataRef as value
    private Map<String,String> csvColumnDetails = new LinkedHashMap<>();
    
    private CSVFileExporter(String filePath, List<CsvColumnDetails> columnDetails) {
	setFilePath(filePath);
	Map<String,String> unsortedCsvColumnDetailsMap = columnDetails.stream().collect(Collectors.toMap(CsvColumnDetails::getHeaderName,CsvColumnDetails::getDataRef));
	unsortedCsvColumnDetailsMap.entrySet()
				.stream()
				.sorted(Map.Entry.comparingByValue()) // ordering by dataCollector name to ensure correct data is written under correct csv header
				.forEachOrdered(x -> csvColumnDetails.put(x.getKey(), x.getValue()));
    }

    @Override
    public void export(Collection<DataCollector> dataCollectors) {
	try {
	    CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
	    
	    // write header row for header names as requested by client
	    String[] headers = csvColumnDetails.keySet().toArray(new String[csvColumnDetails.keySet().size()]);
	    csvWriter.writeNext(headers);
	    
	    // get data for relevant data collectors only (which the client has asked for)
	    Collection<DataCollector> relevantDataCollectors = dataCollectors.stream()
		    .sorted() // ordering by dataCollector name to ensure correct data is written under correct csv header
		    .filter(collector -> csvColumnDetails.values().contains(collector.getName()))
		    .collect(Collectors.toList());
	    
	    String[][] data = getCSVLinesFrom(relevantDataCollectors);
	    Arrays.stream(data).forEach(csvWriter::writeNext); // write data
	    csvWriter.close();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    // TODO figure out why this method is getting called twice.
    private String[][] getCSVLinesFrom(Collection<DataCollector> dataCollectors) {
	
	// map whose key is csvHeaderName and value is dataCollector whose data the csvColumn will hold.
	// This map is needed since the relevantDataCollectors passed to this method might be less in
	// case there are different csv columns with same dataCollector.
	// So we need a map which will hold dataCollector as value for each CSV column header (map key)
	Map<String,DataCollector> csvColumnData = new LinkedHashMap<>();
	csvColumnDetails.entrySet()
			.stream()
			.forEachOrdered(entry -> csvColumnData.put(entry.getKey(), getDataCollector(entry.getValue(), dataCollectors)));
	
	
	// list of list. The inner list is a list of data that each DataCollector holds 
	List<List<Object>> dataAsLists = csvColumnData.values().stream()
						.map(collector -> collector.getData())
						.map(collector -> new ArrayList<>(collector))
						.collect(Collectors.toList());
	
	List<Integer> dataQuantities = dataCollectors.stream()
							.map(collector -> collector.getData().size())
							.collect(Collectors.toList());
	int totalLines = Collections.max(dataQuantities);
	int totaldataCollectors = csvColumnData.values().size();
	
	String[][] data = new String[totalLines][totaldataCollectors];
	
	
	// TODO Re-factor to simplify
	IntStream.range(0, totalLines)
		.forEach(lineNum -> {
		    IntStream.range(0, totaldataCollectors)
		    		.forEach(collectorNum -> {
		    		    try {
		    			Object colData = dataAsLists.get(collectorNum).get(lineNum);
		    			if(null != colData)
		    			    data[lineNum][collectorNum] = colData.toString();
		    			else
		    			data[lineNum][collectorNum] = "";
		    		    } catch(Exception e) {
		    			// TODO handle it
		    		    }
		    		});
		});
	
	return data;
		
    }

    private DataCollector getDataCollector(String collectorName, Collection<DataCollector> dataCollectors) {
	try {
	    return dataCollectors.stream().filter(collector -> collector.getName().equals(collectorName)).findFirst().get();
	} catch(Exception e) {
	    throw new NoSuchElementException("No collector found for name [" + collectorName + "]. Please check if ["
		    + collectorName + "] is associated with any data generation query");
	}
    }

    @Override
    public void setFilePath(String path) {
	this.filePath = path;
    }

    public static CSVFileExporter from(String filePath, List<CsvColumnDetails> columnDetails) {
	return new CSVFileExporter(filePath, columnDetails);
    }

}
