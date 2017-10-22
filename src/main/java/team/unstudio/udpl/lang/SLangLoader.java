package team.unstudio.udpl.lang;

import java.io.File;

import team.unstudio.udpl.util.FileUtils;

/**
 * Created by trychen on 17/7/28.
 */
public class SLangLoader {
    /**
     * 读取文件内容并通过SLangSpliter读取所有语言项
     * @param separator
     * @param file
     * @return
     * @throws Exception
     */
    public SLang[] loadFromFile(String separator,File file) throws Exception {
        return SLangSpliter.split(separator, FileUtils.readFile2Array(file, "utf-8"));
    }
}
