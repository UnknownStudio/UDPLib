package team.unstudio.udpl.api.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Utils {

    /**
     * 载入配置文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static FileConfiguration loadConfiguration(File file) throws IOException {
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

        if (!file.exists()) file.createNewFile();

        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * 读取文件内容到List
     *
     * @param file 文件
     * @param list 要写入到的List
     * @throws Exception
     */
    public static void readFile2List(File file, List<String> list) throws Exception {
        //TODO: 读文件
    }

    /**
     * 读取文件内容到数组
     *
     * @param file 文件
     * @return 文件内容分行的数组
     * @throws Exception
     */
    public static String[] readFile2Array(File file) throws Exception {
        List<String> list = new ArrayList<>();
        readFile2List(file, list);
        return list.toArray(new String[0]);
    }
}
