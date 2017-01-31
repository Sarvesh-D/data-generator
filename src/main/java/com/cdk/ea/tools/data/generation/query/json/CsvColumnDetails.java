package com.cdk.ea.tools.data.generation.query.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of="headerName")
public class CsvColumnDetails {
    
    @NonNull private final String headerName;
    private final String dataRef;
    
}
