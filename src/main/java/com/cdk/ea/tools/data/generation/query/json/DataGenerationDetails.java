package com.cdk.ea.tools.data.generation.query.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = { "name", "quantity" })
public class DataGenerationDetails {

    private String name;
    private DataDetails details;
    private int quantity;

}