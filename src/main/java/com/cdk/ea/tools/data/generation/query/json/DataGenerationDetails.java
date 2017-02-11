package com.cdk.ea.tools.data.generation.query.json;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Value Class for holding single Data Generation Query.
 * 
 * @author Sarvesh Dubey <sarvesh.dubey@cdk.com>
 * @since 11-02-2017
 * @version 1.0
 */
@Getter
@Setter
@ToString(of = { "name", "quantity" })
public class DataGenerationDetails {

    private String name;
    private DataDetails details;
    private int quantity;

}
