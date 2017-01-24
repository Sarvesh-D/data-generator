package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import com.cdk.ea.data.core.Identifiers;
import com.cdk.ea.data.core.StringProperties;
import com.cdk.ea.data.exception.InterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;
import com.cdk.ea.data.types.StringType.StringTypeBuilder;

public class StringTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	StringTypeBuilder stringTypeBuilder = new StringTypeBuilder();
	EnumSet<StringProperties> stringProps = EnumSet.noneOf(StringProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream()
	    .map(StringProperties::of)
	    .forEach(stringProps::add);
	} catch(Exception e) {
	    throw new InterpretationException("Invalid String Property. Possible Values are : "+StringProperties.ENUM_MAP.keySet());
	}
	
	// default string type
	if(stringProps.isEmpty())
	    stringProps.add(StringProperties.ALPHA);
	
	final String prefix = getPrefix(identifiers);
	final String suffix = getSuffix(identifiers);
	final int dataLength = getDataLength(identifiers);

	final int baseStringLength = prefix.length() + suffix.length();
	Assert.assertTrue("Total String length [" + dataLength
		+ "] should be greater or equal to Base String length [" + baseStringLength + "]",
		dataLength >= baseStringLength);

	stringTypeBuilder.setDataType(getDataType(identifiers));
	stringTypeBuilder.setTypeProperties(stringProps);
	stringTypeBuilder.setLength(dataLength - baseStringLength);
	stringTypeBuilder.setPrefix(prefix);
	stringTypeBuilder.setSuffix(suffix);

	queryBuilder.setTypeBuilder(stringTypeBuilder);
    }
    
    private String getPrefix(String... identifiers) {
	Optional<String> prefix = Arrays.stream(identifiers)
		.filter(i -> i.charAt(0) == Identifiers.PREFIX.getIdentifier())
		.map(i -> i.substring(1))
		.findFirst();
	
	return prefix.isPresent() ? StringUtils.trimToEmpty(prefix.get()) : "";
    }
    
    private String getSuffix(String... identifiers) {
	Optional<String> suffix = Arrays.stream(identifiers)
		.filter(i -> i.charAt(0) == Identifiers.SUFFIX.getIdentifier())
		.map(i -> i.substring(1))
		.findFirst();
	
	return suffix.isPresent() ? StringUtils.trimToEmpty(suffix.get()) : "";
    }

}
