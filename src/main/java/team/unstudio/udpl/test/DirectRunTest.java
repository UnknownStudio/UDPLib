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
        guess("UTF-8");
        guess("UTF-16");
        guess("UTF-32");
        guess("GB2312");
        guess("GBK");
        guess("GB18030");
        guess("BIG5");
        guess("ISO-8859-1");
        guess("Unicode");
        guess("CP1250");
    }

    public static void guess(String code) throws IOException {
        File file = new File("/Development/encoding_test.yml");
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("TODO: 大家好！123/*-§".getBytes(Charset.forName(code)));
        fileOutputStream.close();
        System.out.println(code + "|" + EncodingDetect.getJavaEncode(file));
        file.delete();
    }
}
