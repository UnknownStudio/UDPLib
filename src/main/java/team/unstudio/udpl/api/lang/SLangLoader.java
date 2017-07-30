package team.unstudio.udpl.api.lang;

import team.unstudio.udpl.api.util.Utils;

import java.io.File;

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
        return SLangSpliter.split(separator, Utils.readFile2Array(file, "utf-8"));
    }
}
