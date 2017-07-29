package team.unstudio.udpl.test;

import team.unstudio.udpl.api.config.EncodingDetect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by trychen on 17/7/29.
 */
public class DirectRunTest {
    public static void main(String[] args) throws IOException {
        File file = new File("/Development/encoding_test.yml");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("大家好！".getBytes(Charset.forName("UTF-8")));
        fileOutputStream.close();
        System.out.println(EncodingDetect.getJavaEncode(file));
//        file.deleteOnExit();
    }
}
