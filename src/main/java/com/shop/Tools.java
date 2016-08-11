package com.shop;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by Vladyslav Usenko on 11.08.2016.
 */
public class Tools {
    public static boolean isValidUUID(String string) {
        try {
            UUID.fromString(string);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public static Object parseObjectFromJSON(String JSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON, Product.class);
    }
}
