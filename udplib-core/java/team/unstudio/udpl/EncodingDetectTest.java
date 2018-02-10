package team.unstudio.udpl;

import org.junit.AfterClass;
import org.junit.Test;
import team.unstudio.udpl.config.EncodingDetect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by trychen on 17/7/29.
 */
public class EncodingDetectTest {
    private static File file = new File("encoding_tester.yml");

    @Test
    public void guessingFileEncoding() throws IOException {
        guess("UTF-8", Collections.singletonList("Unicode"));
        guess("UTF-16", Collections.singletonList("Unicode"));
        guess("UTF-32", Arrays.asList("ASCII", "Unicode"));
        guess("GB2312", Collections.emptyList());
        guess("GBK", Collections.singletonList("GB2312"));
        guess("GB18030", Collections.singletonList("GB2312"));
        guess("BIG5", Collections.emptyList());
        guess("ISO-8859-1", Collections.singletonList("ASCII"));
        guess("Unicode", Collections.emptyList());
        guess("CP1250", Collections.singletonList("ASCII"));
    }

    @AfterClass
    public static void delFile(){
        file.delete();
    }

    public void guess(String code, List<String> expect) throws IOException {
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write("TODO: 大家好！123/*-§".getBytes(Charset.forName(code)));
        fileOutputStream.close();
        String encoding = EncodingDetect.getJavaEncode(file);
        file.delete();
        if (!code.equals(encoding) && !expect.contains(encoding)){
            fail(String.format("Can't guess file encoding %s as %s, %s found!", code, Arrays.toString(expect.toArray()), encoding));
        }
    }
}
