package com.ds.tools.data.generator.generators;

import static org.junit.Assert.assertNotNull;

import com.ds.tools.data.generator.common.NumberUtils;
import com.ds.tools.data.generator.common.StringUtils;
import com.ds.tools.data.generator.types.NumberType;

import lombok.RequiredArgsConstructor;

/**
 * Generator class for generating random numbers.
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 10-02-2017
 * @version 1.0
 * @see NumberType
 */
@RequiredArgsConstructor
public class NumberGenerator implements Generator<Number> {

    private final NumberType numberType;

    /**
     * Factory method to instantiate {@link NumberGenerator}
     *
     * @param numberType
     *            from which the generator is to be instantiated.
     * @return {@link NumberGenerator}
     */
    public static NumberGenerator of(final NumberType numberType) {
        assertNotNull("Number Type cannot be null", numberType);
        return new NumberGenerator(numberType);
    }

    /**
     * {@inheritDoc}. This generator generates a random number.
     */
    @Override
    public Number generate() {
        /*
         * TODO Once the control gets inside the loop for last character to be appended
         * on the buffer, the map function on the Stream will be called
         * [NumberProperties.values().length - 1] times unnecessarily. Fix this.
         */
        final StringBuilder generatedNumber = new StringBuilder(numberType.getLength());

        /**
         * add first number as natural to avoid 0 in the beginning. 0 in beginning
         * causes length of generated number to be 1 less than desired example:
         * Long.valueOf("01") gives 1 instead of 01.
         */
        generatedNumber.append(NumberUtils.randomNatural());
        while (StringUtils.canAppend(generatedNumber)) {
            numberType.getProperties()
                      .stream()
                      .forEach(property -> StringUtils.append(generatedNumber, property.getGenerator()
                                                                                       .generate()));
        }
        return Long.valueOf(generatedNumber.toString());
    }

}
