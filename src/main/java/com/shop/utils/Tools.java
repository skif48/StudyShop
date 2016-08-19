package com.shop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.Attribute;
import com.shop.entity.AttributeValue;
import com.shop.entity.Product;

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
}
