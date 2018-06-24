package com.ds.tools.data.generator.query.json;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Value Class for holding single CLI query.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 */
@Getter
@Setter
@ToString
public class JsonQueryDetails {

    private List<DataGenerationDetails> dataDetails = new ArrayList<>();

    private boolean exportToCsv;

    private List<DataExportDetails> exportDetails = new ArrayList<>();

    private DefaultOverrides defaults;

}
