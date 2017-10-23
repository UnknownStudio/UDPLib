package team.unstudio.udpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import team.unstudio.udpl.util.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FileUtilsTest {
    private static File file = new File("file_utils_tester");
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static String[] DATA = {
            "Hello World!",
            "中文测试",
            "!_!*@)&#%§¶•∞"
    };

    @Before
    public void resetFile() throws IOException {
        FileWriter fw = new FileWriter(file);

        for (int i = 0; i < DATA.length; i++) {
            if (i < DATA.length - 1) fw.write(DATA[i] + LINE_SEPARATOR);
            else fw.write(DATA[i]);
        }

        fw.close();

    }

    @After
    public void delFile(){
        file.delete();
    }

    @Test
    public void readFile() throws Exception {
        assertArrayEquals(DATA, FileUtils.readFile2Array(file, Charset.defaultCharset().name()));
    }
}