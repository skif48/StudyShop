package com.shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.Characteristic;
import com.shop.entity.Product;

import java.io.IOException;
import java.nio.ByteBuffer;
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

    public static Object parseProductFromJSON(String JSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON, Product.class);
    }

    public static Object parseCharacteristicFromJSON(String JSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON, Characteristic.class);
    }

    public static Object parseAttributeFromJSON(String JSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON, Attribute.class);
    }

    public static Object parseAttributeValueFromJSON(String JSON) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(JSON, AttributeValue.class);
    }

}
