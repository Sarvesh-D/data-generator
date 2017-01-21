package com.cdk.ea.data.query.converter;

import java.io.File;
import java.io.IOException;

import com.cdk.ea.data.query.holder.JsonQueryHolder;
import com.cdk.ea.data.query.json.JsonQueryDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.ToString;

@ToString
public class JsonQueryConverter implements QueryConverter {
    
    @Getter private JsonQueryDetails jsonQueryDetails;

    @Override
    public void init(String... args) {
	ObjectMapper mapper = new ObjectMapper();
	try {
	    // convert one json file to CMD query. TODO see about accepting multiple json files
	    jsonQueryDetails = mapper.readValue(new File(args[1]), JsonQueryDetails.class);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

}
