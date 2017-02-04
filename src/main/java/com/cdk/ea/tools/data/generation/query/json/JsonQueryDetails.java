package com.cdk.ea.tools.data.generation.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class JsonQueryDetails {

    private List<DataGenerationDetails> dataDetails = new ArrayList<>();
    private boolean exportToCsv;
    private List<DataExportDetails> exportDetails = new ArrayList<>();
    private DefaultOverrides defaults;

}
