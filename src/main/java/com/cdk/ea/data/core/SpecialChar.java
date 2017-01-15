package com.cdk.ea.data.core;

import com.cdk.ea.data.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SpecialChar implements Identifier<Character> {

    EXCLAMATION('!'),
    HASH('#'),
    PERCENT('%'),
    AT('@'),
    CARAT('^'),
    DOLLAR('$'),
    AMPERSAND('&');

    @Getter private Character identifier;

}
