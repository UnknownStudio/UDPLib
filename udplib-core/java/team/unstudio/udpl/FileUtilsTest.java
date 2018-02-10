package team.unstudio.udpl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import team.unstudio.udpl.util.FileUtils;

import java.io.*;

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
        file.createNewFile();

        Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8"));
        for (int i = 0; i < DATA.length; i++) {
            writer.write(DATA[i]);
            if (i != DATA.length - 1) writer.write(LINE_SEPARATOR);
        }
        writer.flush();
        writer.close();
    }

    @After
    public void delFile(){
//        file.delete();
    }

    @Test
    public void readFile() throws Exception {
        assertArrayEquals(DATA, FileUtils.readFile2Array(file, "UTF-8"));
    }
}