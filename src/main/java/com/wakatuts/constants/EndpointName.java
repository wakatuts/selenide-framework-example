package com.wakatuts.constants;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakatuts.data.api.APISpecs;
import org.apache.commons.text.CaseUtils;

import java.io.File;
import java.io.IOException;

public enum EndpointName {

    LOGIN,
    ADD_TO_CART,
    VIEW_CART,
    DELETE_ITEM;

    public APISpecs getAPISpecs() {
        String camelCaseValue = CaseUtils.toCamelCase(this.name(), false, '_');

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(new File(Constants.API_PATH + camelCaseValue + ".json"), APISpecs.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
