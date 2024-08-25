package com.wakatuts.api;

import com.google.inject.Inject;
import com.jayway.jsonpath.JsonPath;
import com.wakatuts.config.ConfigFactory;
import com.wakatuts.config.FrameworkConfig;
import com.wakatuts.constants.EndpointName;
import com.wakatuts.data.api.APISpecs;
import com.wakatuts.data.api.WrappedResponse;
import com.wakatuts.data.model.ExecutionData;
import com.wakatuts.logging.MaskedAllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.io.IoBuilder;

import java.io.PrintStream;
import java.util.Map;

import static com.wakatuts.constants.Constants.RESOURCE_PATH;

@Log4j2
public abstract class BaseAPI {

    protected final FrameworkConfig CONFIG = ConfigFactory.config();

    @Inject protected ExecutionData executionData;

    protected RequestSpecification given(EndpointName endpointName, Map<String, Object> customParams) {
        PrintStream logStream = IoBuilder.forLogger(log).buildPrintStream();
        RequestSpecification spec = RestAssured.given()
                .filter(new MaskedAllureRestAssured())
                .filter(RequestLoggingFilter.logRequestTo(logStream))
                .filter(ResponseLoggingFilter.logResponseTo(logStream))
                .config(RestAssured.config().logConfig(LogConfig.logConfig().blacklistHeader("Authorization")))
                .baseUri(CONFIG.apiBaseUrl());
        updateSpecs(spec, endpointName, customParams);
        return spec;
    }

    protected RequestSpecification given(EndpointName endpointName) {
        return given(endpointName, Map.of());
    }

    protected WrappedResponse wrapResponse(EndpointName endpointName, Response response) {
        return new WrappedResponse(endpointName, response);
    }

    private void updateSpecs(RequestSpecification reqSpecs, EndpointName endpointName, Map<String, Object> customParams) {
        APISpecs apiSpecs = endpointName.getAPISpecs();
        Map<String, Object> defaults = apiSpecs.getDefaults();
        Map<String, String> jsonMapping = apiSpecs.getRequestJsonPath();
        Object body = apiSpecs.getRequestTemplate();

        if(executionData.getApiToken() != null) {
            defaults.put("cookie", executionData.getApiToken());
        }

        for(String key : defaults.keySet()) {
            if(jsonMapping.containsKey(key)) JsonPath.parse(body).set(jsonMapping.get(key), defaults.get(key));
        }
        for(String key : customParams.keySet()) {
            if(jsonMapping.containsKey(key)) JsonPath.parse(body).set(jsonMapping.get(key), customParams.get(key));
        }

        reqSpecs.basePath(apiSpecs.getPath())
                .body(body)
                .accept(apiSpecs.getAccept())
                .contentType(apiSpecs.getContentType());
    }

}
