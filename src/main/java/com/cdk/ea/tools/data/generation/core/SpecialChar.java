package com.cdk.ea.tools.data.generation.core;

import com.cdk.ea.tools.data.generation.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum SpecialChar implements Identifier<Character> {

    EXCLAMATION('!'), HASH('#'), PERCENT('%'), AT('@'), CARAT('^'), DOLLAR('$'), AMPERSAND('&');

    @Getter
    private Character identifier;

}
