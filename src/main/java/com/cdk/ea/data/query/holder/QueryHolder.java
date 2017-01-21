package com.cdk.ea.data.query.holder;

import com.cdk.ea.data.query.converter.QueryConverter;

public interface QueryHolder<T extends QueryConverter> {
    
    String getQuery(T queryConverter);

}
