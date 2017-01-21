package com.cdk.ea.data.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DataExportDetails {
    
    private String csvFile;
    private List<CsvColumnDetails> csvDetails = new ArrayList<>();

}
