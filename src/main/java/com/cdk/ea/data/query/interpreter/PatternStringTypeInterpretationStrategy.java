package com.cdk.ea.data.query.interpreter;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Optional;

import org.junit.Assert;

import com.cdk.ea.data.core.PatternStringProperties;
import com.cdk.ea.data.core.StringProperties;
import com.cdk.ea.data.exception.InterpretationException;
import com.cdk.ea.data.query.Query.QueryBuilder;
import com.cdk.ea.data.types.PatternStringType.PatternStringTypeBuilder;
import com.cdk.ea.data.types.StringType.StringTypeBuilder;

public class PatternStringTypeInterpretationStrategy extends AbstractTypeInterpretationStrategy {

    @Override
    public void doInterpret(QueryBuilder queryBuilder, String... identifiers) {
	PatternStringTypeBuilder patternStringTypeBuilder = new PatternStringTypeBuilder();
	StringTypeBuilder stringTypeBuilder = new StringTypeBuilder();
	EnumSet<StringProperties> stringProps = EnumSet.noneOf(StringProperties.class);
	EnumSet<PatternStringProperties> patternStringProps = EnumSet.noneOf(PatternStringProperties.class);

	try {
	    getPropertyIdentifiers(identifiers).stream()
	    					.forEach(identifier -> {
	    					    try {
	    						stringProps.add(StringProperties.of(identifier));
	    					    } catch(Exception e) {
	    						patternStringProps.add(PatternStringProperties.of(identifier));
	    					    }
	    					});
	} catch(Exception e) {
	    throw new InterpretationException("Invalid Pattern String Property. Possible Values are : "+StringProperties.ENUM_MAP.keySet()+" , "+PatternStringProperties.ENUM_MAP.keySet());
	}

	final int patternStringLength = getDataLength(identifiers);
	final String prefix = getPrefix(identifiers);
	final String suffix = getSuffix(identifiers);
	
	final int baseStringLength = prefix.length() + suffix.length();
	Assert.assertTrue("Pattern String length [" + patternStringLength
		+ "] should be greater or equal to Base String length [" + baseStringLength + "]",
		patternStringLength >= baseStringLength);
	
	stringTypeBuilder.setTypeProperties(stringProps);
	stringTypeBuilder.setLength(patternStringLength - baseStringLength);
	
	patternStringTypeBuilder.setDataType(getDataType(identifiers));
	patternStringTypeBuilder.setStringType(stringTypeBuilder.buildType());
	patternStringTypeBuilder.setLength(patternStringLength);
	patternStringTypeBuilder.setTypeProperties(patternStringProps);
	patternStringTypeBuilder.setPrefix(prefix);
	patternStringTypeBuilder.setSuffix(suffix);
	
	queryBuilder.setTypeBuilder(patternStringTypeBuilder);

    }
    
    private String getPrefix(String... identifiers) {
	Optional<String> prefix = Arrays.stream(identifiers)
		.filter(i -> i.charAt(0) == PatternStringProperties.PREFIX.getIdentifier())
		.map(i -> i.substring(1))
		.findFirst();
	
	return prefix.isPresent() ? prefix.get() : "";
    }
    
    private String getSuffix(String... identifiers) {
	Optional<String> suffix = Arrays.stream(identifiers)
		.filter(i -> i.charAt(0) == PatternStringProperties.SUFFIX.getIdentifier())
		.map(i -> i.substring(1))
		.findFirst();
	
	return suffix.isPresent() ? suffix.get() : "";
    }

}
