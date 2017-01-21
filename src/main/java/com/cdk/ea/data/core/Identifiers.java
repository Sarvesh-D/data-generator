package com.cdk.ea.data.core;

import com.cdk.ea.data.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Identifiers implements Identifier<Character> {

    TYPE(':'),
    PROPERTY('-'),
    LENGTH('l'),
    QUANTITY('='),
    QUERY_SEPARATOR('|'),
    DATA_COLLECTOR_PREFIX('@'),
    DATA_GEN_QUERY_PREFIX('('),
    DATA_GEN_QUERY_SUFFIX(')'),
    FILE('f'),
    DATA_EXPORT_QUERY_PREFIX('<'),
    DATA_EXPORT_QUERY_SUFFIX('>'),
    CSV_HEADER_PREFIX('_'),
    CSV_COL_DATA_REF('=');
    
    @Getter private final Character identifier;
    
}
