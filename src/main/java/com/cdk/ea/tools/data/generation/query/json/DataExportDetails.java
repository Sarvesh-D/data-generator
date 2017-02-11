package com.cdk.ea.tools.data.generation.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Value Class for holding single Data Export Query
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 11-02-2017
 * @version 1.0
 */
@Getter
@Setter
@ToString(of = "csvFile")
public class DataExportDetails {

    private String csvFile;
    private List<CsvColumnDetails> csvDetails = new ArrayList<>();

}
