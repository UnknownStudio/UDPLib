package team.unstudio.udpl.api.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * 读取文件内容到List
     *
     * @param file 文件
     * @param list 要写入到的List
     * @throws Exception
     */
    public static void readFile2List(File file, List<String> list, String code) throws Exception {
        BufferedReader fr;
        try {
            String myCode = code!=null&&!"".equals(code) ? code : "UTF-8";
            InputStreamReader read = new InputStreamReader(new FileInputStream(
                    file), myCode);

            fr = new BufferedReader(read);
            String line = null;
            int flag=1;
            // 读取每一行，如果结束了，line会为空
            while ((line = fr.readLine()) != null && line.trim().length() > 0) {
                if(flag==1) {
                    line=line.substring(1);//去掉文件头
                    flag++;
                }
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
     * @throws Exception
     */
    public static String[] readFile2Array(File file, String code) throws Exception {
        List<String> list = new ArrayList<>();
        readFile2List(file, list, code);
        return list.toArray(new String[0]);
    }
}
