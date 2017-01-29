package com.cdk.ea.data.core;

import com.cdk.ea.data.common.CMDLineArgHelper;
import com.cdk.ea.data.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Identifiers implements Identifier<Character>, CMDLineArgHelper {

    TYPE(':', "Type Identifier"),
    PROPERTY('-', "Property Identifier"),
    LENGTH('l', "Data Length Identifier"),
    QUANTITY('=', "Quantity Identifier"),
    QUERY_SEPARATOR('|', "Query Separator"),
    DATA_COLLECTOR_PREFIX('@', "Data Collector Name Prefix"),
    DATA_GEN_QUERY_PREFIX('(', "Data Generation Query Start"),
    DATA_GEN_QUERY_SUFFIX(')', "Data Generation Query End"),
    FILE('f', "Export to CSV file"),
    DATA_EXPORT_QUERY_PREFIX('<', "Data Export Query Start"),
    DATA_EXPORT_QUERY_SUFFIX('>', "Data Export Query End"),
    CSV_HEADER_PREFIX('_', "CSV Header prefix"),
    OVERRIDE('o', "Default Override Identifier"),
    CSV_COL_DATA_REF('=', "CSV Column Data Reference Identifier"),
    PREFIX('P', "String Prefix Identifier"),
    SUFFIX('S', "String Suffix Identifier");
    
    @Getter private final Character identifier;
    
    @Getter private final String help;
    
}
