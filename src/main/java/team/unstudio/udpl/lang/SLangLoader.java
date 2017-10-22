package team.unstudio.udpl.lang;

import java.io.File;

import team.unstudio.udpl.util.FileUtils;

/**
 * Created by trychen on 17/7/28.
 */
public interface SLangLoader {
    /**
     * 读取文件内容并通过SLangSpliter读取所有语言项
     * @param separator 分隔符
     * @param file 文件
     * @throws Exception 文件读取错误
     */
    static SLang[] loadFromFile(String separator,File file) throws Exception {
        return SLangSpliter.split(separator, FileUtils.readFile2Array(file, "utf-8"));
    }
}
