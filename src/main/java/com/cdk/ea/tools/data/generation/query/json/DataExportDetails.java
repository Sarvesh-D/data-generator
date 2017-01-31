package com.cdk.ea.tools.data.generation.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of="csvFile")
public class DataExportDetails {
    
    private String csvFile;
    private List<CsvColumnDetails> csvDetails = new ArrayList<>();

}
