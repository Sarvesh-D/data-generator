package com.cdk.ea.data.query.holder;

import org.apache.commons.lang.StringUtils;

import com.cdk.ea.data.query.converter.CmdQueryConverter;

import lombok.ToString;

@ToString
public class CmdQueryHolder implements QueryHolder<CmdQueryConverter> {
    
    @Override
    public String getQuery(CmdQueryConverter cmdQueryConverter) {
	return StringUtils.join(cmdQueryConverter.getCmdQueryDetails(), " ");
    }

}
