package com.cdk.ea.tools.data.generation.query.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DataDetails {
    
    private String type;
    private Set<String> properties = new HashSet<>();
    private int length;
    private List<String> list = new ArrayList<>();
    private String regex;
    private String prefix;
    private String suffix;

}
