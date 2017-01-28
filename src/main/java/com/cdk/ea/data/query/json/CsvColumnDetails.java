package com.cdk.ea.data.query.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of="headerName")
public class CsvColumnDetails {
    
    private final String headerName;
    private final String dataRef;
    
}
