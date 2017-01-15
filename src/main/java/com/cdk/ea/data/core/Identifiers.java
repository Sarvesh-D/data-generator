package com.cdk.ea.data.core;

import com.cdk.ea.data.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Identifiers implements Identifier<Character> {

    TYPE(':'),
    PROPERTY('-'),
    LENGTH('l'),
    QUERY_SEPARATOR('|'),
    QUANTITY('=');
    
    @Getter private final Character identifier;
    
}
