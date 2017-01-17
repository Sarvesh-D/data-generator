package com.cdk.ea.data.exporters;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.cdk.ea.data.generators.DataCollector;
import com.opencsv.CSVWriter;

public class CSVFileExporter implements FileExporter {
    
    private String filePath;
    
    private String[] csvHeaders;
    
    private CSVFileExporter(String filePath, String... csvHeaders) {
	setFilePath(filePath);
	this.csvHeaders = csvHeaders;
    }

    @Override
    public void export(Collection<DataCollector> dataCollector) {
	try {
	    CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath));
	    csvWriter.writeNext(csvHeaders); // write header row
	    
	    String[][] data = getCSVLinesFrom(dataCollector);
	    Arrays.stream(data).forEach(csvWriter::writeNext); // write data
	    csvWriter.close();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
    
    private String[][] getCSVLinesFrom(Collection<DataCollector> dataCollector) {
	List<List<Object>> dataAsLists = dataCollector.stream()
						.map(collector -> collector.getData())
						.map(collector -> new ArrayList<>(collector))
						.collect(Collectors.toList());
	
	List<Integer> dataQuantities = dataCollector.stream()
							.map(collector -> collector.getData().size())
							.collect(Collectors.toList());
	int maxLines = Collections.max(dataQuantities);
	int dataCollectors = dataCollector.size();
	
	String[][] data = new String[maxLines][dataCollectors];
	IntStream.range(0, maxLines)
		.forEach(lineNum -> {
		    IntStream.range(0, dataCollectors)
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

    @Override
    public void setFilePath(String path) {
	this.filePath = path;
    }

    public static CSVFileExporter from(String filePath, String... csvHeaders) {
	return new CSVFileExporter(filePath, csvHeaders);
    }

}
