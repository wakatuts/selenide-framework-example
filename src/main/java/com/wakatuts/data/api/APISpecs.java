package com.wakatuts.data.api;

import lombok.Getter;

import java.util.Map;

@Getter
public class APISpecs {

    private String path;
    private String accept;
    private String contentType;
    private Map<String, Object> defaults;
    private Map<String, String> requestJsonPath;
    private Object requestTemplate;
    private Map<String, String> responseJsonPath;

}
