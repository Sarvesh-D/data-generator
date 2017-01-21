package com.cdk.ea.data.query.json;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CsvColumnDetails {
    
    private String headerName;
    private String dataRef;

}
