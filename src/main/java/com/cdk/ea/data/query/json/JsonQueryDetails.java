package com.cdk.ea.data.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class JsonQueryDetails {
    
    private List<DataGenerationDetails> dataDetails = new ArrayList<>();
    private boolean exportToCsv;
    private List<DataExportDetails> exportDetails = new ArrayList<>();
    private DefaultOverrides defaults;
    
}
