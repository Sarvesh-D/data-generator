package com.ds.tools.data.generator.core;

import com.ds.tools.data.generator.types.TypeProperties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Container for the Properties supported by {@link DataType#FAKER}. The Faker
 * Key must correspond to key used by Faker to generate data.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 18-04-2017
 * @version 1.5
 */
@RequiredArgsConstructor
@Getter
public class FakerProperties implements TypeProperties {

    private final String fakerKey;

}
