package com.wakatuts.data.api;

import com.jayway.jsonpath.JsonPath;
import com.wakatuts.constants.EndpointName;
import io.restassured.response.Response;

public class WrappedResponse {

    private final EndpointName endpointName;
    private final Response response;

    public WrappedResponse(EndpointName endpointName, Response response) {
        this.endpointName = endpointName;
        this.response = response;
    }

    public <T> T getValue(String jsonPathName) {
      return JsonPath.parse(response.getBody())
                .read(endpointName.getAPISpecs().getResponseJsonPath().get(jsonPathName));
    }

}
