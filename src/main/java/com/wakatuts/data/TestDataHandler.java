package com.wakatuts.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakatuts.data.creds.AbstractCredData;
import com.wakatuts.data.creds.UserCredData;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static com.wakatuts.constants.Constants.RESOURCE_PATH;

public class TestDataHandler {

    private static final String USER_CRED_PATH = RESOURCE_PATH + FilenameUtils.separatorsToSystem("/credentials/Users.json");

    public static UserCredData getUserCredData(String alias) {
        return getDataObject(UserCredData.class, USER_CRED_PATH, alias);
    }

    @SuppressWarnings("unchecked")
    private static <T extends AbstractCredData> T getDataObject(Class<T> clazz, String filePath, String alias) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            T[] users = mapper.readValue(new File(filePath), (Class<T[]>) java.lang.reflect.Array.newInstance(clazz, 0).getClass());
            return Arrays.stream(users).filter(d -> d.getAlias().equals(alias)).findFirst().orElseThrow();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
