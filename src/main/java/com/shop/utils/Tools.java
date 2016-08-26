package com.shop.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public static byte[] imageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byte[] imageInByte = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return imageInByte;
    }

    public static BufferedImage byteArrayToBufferedImage(byte[] bytes) throws IOException{
        return ImageIO.read(new ByteArrayInputStream(bytes));
    }
}
