package com.cdk.ea.data.query.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class JsonQueryType {
    
    private String type = "";
    private Set<String> properties = new HashSet<>();
    private List<String> list = new ArrayList<>();
    private String regex = "";
    
}
