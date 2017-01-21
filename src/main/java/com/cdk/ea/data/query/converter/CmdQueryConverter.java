package com.cdk.ea.data.query.converter;

import lombok.Getter;

public class CmdQueryConverter implements QueryConverter {
    
    @Getter private String[] cmdQueryDetails;

    @Override
    public void init(String... args) {
	this.cmdQueryDetails = args;
    }

}
