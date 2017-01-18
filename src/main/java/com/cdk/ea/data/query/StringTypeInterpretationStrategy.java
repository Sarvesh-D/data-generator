package com.cdk.ea.data.query;

import java.util.EnumSet;

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

	stringTypeBuilder.setDataType(getDataType(identifiers));
	stringTypeBuilder.setTypeProperties(stringProps);
	stringTypeBuilder.setLength(getDataLength(identifiers));

	queryBuilder.setTypeBuilder(stringTypeBuilder);
    }

}
