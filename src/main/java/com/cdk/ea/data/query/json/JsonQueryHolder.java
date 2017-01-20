package com.cdk.ea.data.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JsonQueryHolder {
    
    private List<JsonQueryType> dataTypes = new ArrayList<>();
    private boolean exportToCsv;
    private String csvFile;
    private List<String> csvHeaders = new ArrayList<>();

}
