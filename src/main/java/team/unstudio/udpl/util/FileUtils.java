package team.unstudio.udpl.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public interface FileUtils {
	
    /**
     * 读取文件内容到List
     *
     * @param file 文件
     * @param list 要写入到的List
     * @param code 编码
     * @throws Exception
     */
    static void readFile2List(File file, List<String> list, String code) throws Exception {
        BufferedReader fr;
        try {
            String myCode = code!=null&&!"".equals(code) ? code : Charset.defaultCharset().name();
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), myCode);

            fr = new BufferedReader(read);
            String line = null;
            while ((line = fr.readLine()) != null && line.trim().length() > 0) {
                list.add(line);
            }
            fr.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件内容到数组
     *
     * @param file 文件
     * @return 文件内容分行的数组
     * @param code 编码
     * @throws Exception
     */
    static String[] readFile2Array(File file, String code) throws Exception {
        List<String> list = new ArrayList<>();
        readFile2List(file, list, code);
        return list.toArray(new String[0]);
    }
}
