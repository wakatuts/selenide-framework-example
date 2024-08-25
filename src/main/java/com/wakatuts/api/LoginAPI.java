package com.wakatuts.api;

import com.wakatuts.constants.EndpointName;
import com.wakatuts.data.creds.UserCredData;
import io.restassured.response.Response;
import org.apache.commons.lang3.RegExUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

public class LoginAPI extends BaseAPI {

    public Response loginUsing(UserCredData userCredData) {
        Map<String, Object> data = Map.of("username", userCredData.getUsername(),
                "password", Base64.getEncoder()
                        .encodeToString(userCredData.getPassword().getBytes(StandardCharsets.UTF_8)));
        return given(EndpointName.LOGIN, data).post();
    }

    public String getTokenUsing(UserCredData userCredData) {
        Response response = loginUsing(userCredData);
        String responseString = response.getBody().asString();
        return RegExUtils.replacePattern(responseString, "Auth_token: (\\w+)", "$1")
                .replaceAll("\"", "").trim();
    }

}
