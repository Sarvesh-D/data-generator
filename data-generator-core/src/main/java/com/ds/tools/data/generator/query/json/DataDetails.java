package com.ds.tools.data.generator.query.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ds.tools.data.generator.core.DataType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Value Class for holding details of {@link DataType}
 *
 * @author <a href="https://github.com/Sarvesh-D/">Sarvesh Dubey</a>
 * @since 11-02-2017
 * @version 1.0
 */
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

    /**
     * @since 1.5
     */
    private String locale;

}
