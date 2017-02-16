package com.cdk.ea.tools.data.generation.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.cdk.ea.tools.data.generation.core.Constants;
import com.cdk.ea.tools.data.generation.core.Defaults;
import com.cdk.ea.tools.data.generation.core.Identifiers;
import com.cdk.ea.tools.data.generation.core.StringProperties;
import com.cdk.ea.tools.data.generation.exception.PropertiesInterpretationException;
import com.cdk.ea.tools.data.generation.exception.QueryInterpretationException;
import com.cdk.ea.tools.data.generation.query.Query.QueryBuilder;
import com.cdk.ea.tools.data.generation.types.StringType;
import com.cdk.ea.tools.data.generation.types.StringType.StringTypeBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementation for interpreting details of {@link StringType} from
 * identifiers.
 * 
 * @author <a href="mailto:sarvesh.dubey@cdk.com">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 * @see StringType
 * @see StringTypeBuilder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
class StringTypeInterpreter extends AbstractTypeInterpreter {

    /**
     * Interprets and populates the {@link StringTypeBuilder} and attaches it to
     * {@link QueryBuilder}
     * 
     * @throws PropertiesInterpretationException
     *             if invalid {@link StringProperties} are found
     * @throws QueryInterpretationException
     *             if baseString length is greater then finalString length in
     *             case Prefix and/or Suffix are used.
     */
    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	StringTypeBuilder stringTypeBuilder = new StringTypeBuilder();
	EnumSet<StringProperties> stringProps = EnumSet.noneOf(StringProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream().map(StringProperties::of).forEach(stringProps::add);
	} catch (Exception e) {
	    throw new PropertiesInterpretationException(
		    "Invalid String Property. Possible Values are : " + StringProperties.getEnumMap().keySet());
	}

	// default string type
	if (stringProps.isEmpty()) {
	    log.warn("No String Properties specified. Defaulting to ALPHA String.");
	    stringProps.add(Defaults.DEFAULT_STRING_PROP);
	}

	final String prefix = getPrefix(identifiers);
	final String suffix = getSuffix(identifiers);
	final int dataLength = getDataLength(identifiers);

	final int baseStringLength = prefix.length() + suffix.length();
	if (dataLength < baseStringLength) {
	    throw new QueryInterpretationException(
		    String.format("Total String length [%d] should be greater or equal to Base String length [%d]",
			    dataLength, baseStringLength));
	}

	stringTypeBuilder.setDataType(getDataType(identifiers));
	stringTypeBuilder.setTypeProperties(stringProps);
	stringTypeBuilder.setLength(dataLength - baseStringLength);
	stringTypeBuilder.setPrefix(prefix);
	stringTypeBuilder.setSuffix(suffix);

	queryBuilder.setTypeBuilder(stringTypeBuilder);
    }

    /**
     * Identifies the Prefix for the StringType
     * 
     * @param identifiers
     *            to identify prefix
     * @return prefix
     */
    private String getPrefix(String... identifiers) {
	Optional<String> prefix = Arrays.stream(identifiers)
		.filter(i -> i.charAt(0) == Identifiers.PREFIX.getIdentifier()).map(i -> i.substring(1)).findFirst();

	return prefix.isPresent() ? StringUtils.trimToEmpty(prefix.get()) : Constants.EMPTY_STRING;
    }

    /**
     * Identifies the Suffix for the StringType
     * 
     * @param identifiers
     *            to identify suffix
     * @return suffix
     */
    private String getSuffix(String... identifiers) {
	Optional<String> suffix = Arrays.stream(identifiers)
		.filter(i -> i.charAt(0) == Identifiers.SUFFIX.getIdentifier()).map(i -> i.substring(1)).findFirst();

	return suffix.isPresent() ? StringUtils.trimToEmpty(suffix.get()) : Constants.EMPTY_STRING;
    }

}
