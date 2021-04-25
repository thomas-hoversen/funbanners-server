package com.example.funbanners.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class CreateBinary {

    // helpful resource: https://mkyong.com/java/how-to-convert-bufferedimage-to-byte-in-java/
    // helpful resource: https://stackoverflow.com/questions/18077072/how-to-convert-multipartfile-into-byte-stream/18077223

    public byte[] toByteArray(MultipartFile f) throws IOException {
        return f.getBytes();
    }

    // bufferedImage to byte[]
    public byte[] imageToBytes(BufferedImage image, String form) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, form, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    // convert byte[] to BufferedImage
    public BufferedImage toBufferedImage(byte[] bytes) throws IOException {
        InputStream is = new ByteArrayInputStream(bytes);
        BufferedImage bi = ImageIO.read(is);
        return bi;
    }

    // convert byte[] to String
    public String byteToString(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    // convert String to byte[]
    public byte[] stringToByte(String string) {
        return Base64.decodeBase64(string);
    }

    public String toByteString(MultipartFile[] files) throws Exception {
        String bString = "";
        for (int i = 0; i < files.length; i++) {
            if (i == files.length - 1) {
                bString += byteToString(toByteArray(files[i]));
            } else {
                bString += byteToString(toByteArray(files[i])) + ",";
            }
        }
        return bString;
    }
}
