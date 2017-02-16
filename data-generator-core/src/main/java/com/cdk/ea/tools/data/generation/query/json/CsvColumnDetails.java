package com.cdk.ea.tools.data.generation.query.json;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Value Class for holding details of CSV Column such as column header and its
 * data reference
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 */
@Getter
@ToString
@RequiredArgsConstructor
@EqualsAndHashCode(of = "headerName")
public class CsvColumnDetails {

    @NonNull
    private final String headerName;
    private final String dataRef;

}
