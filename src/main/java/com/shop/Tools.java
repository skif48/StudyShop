package com.shop;

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
