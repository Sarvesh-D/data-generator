package com.cdk.ea.data.query.holder;

import com.cdk.ea.data.query.converter.JsonQueryConverter;
import com.cdk.ea.data.query.json.JsonQueryDetails;

import lombok.ToString;

@ToString
public class JsonQueryHolder implements QueryHolder<JsonQueryConverter> {
    
    @Override
    public String getQuery(JsonQueryConverter queryConverter) {
	JsonQueryDetails jsonQueryDetails = queryConverter.getJsonQueryDetails();
	return null;
    }

}
