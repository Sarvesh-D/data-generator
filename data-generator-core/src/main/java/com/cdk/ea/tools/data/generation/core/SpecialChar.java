package com.cdk.ea.tools.data.generation.core;

import com.cdk.ea.tools.data.generation.common.Identifier;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Container to hold special characters that will be used to generate Strings
 * which must have special characters.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 09-02-2017
 * @version 1.0
 */
@AllArgsConstructor
public enum SpecialChar implements Identifier<Character> {

    EXCLAMATION('!'), HASH('#'), PERCENT('%'), AT('@'), CARAT('^'), DOLLAR('$'), AMPERSAND('&');

    @Getter
    private Character identifier;

}
