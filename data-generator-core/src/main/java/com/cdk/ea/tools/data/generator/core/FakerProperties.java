package com.cdk.ea.tools.data.generator.core;

import java.util.Locale;

import com.cdk.ea.tools.data.generator.common.Identifier;
import com.cdk.ea.tools.data.generator.types.TypeProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Container for the Properties supported by {@link DataType#FAKER}.
 * The Faker Key must correspond to key used by Faker to generate data.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 18-04-2017
 * @version 1.5
 */
@RequiredArgsConstructor
@Getter
public class FakerProperties implements TypeProperties {

    private final String fakerKey;
    
}
