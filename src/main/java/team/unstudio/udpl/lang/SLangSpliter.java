package team.unstudio.udpl.lang;

import java.util.Locale;

/**
 * Created by trychen on 17/7/11.
 */
public class SLangSpliter {
    /**
     * 分离符号
     */
    public final static String separator = "|";

    /**
     * 使用默认的分割符号分割文本到 SLang
     *
     * @param list
     * @return
     */
    public SLang[] split(String[] list) {
        return split(separator, list);
    }

    /**
     * 分割文本到 SLang
     *
     * @param separator 分割符
     * @param list      文本行数据
     * @return
     */
    public static SLang[] split(String separator, String[] list) {
        if (list.length < 0) return new SLang[0];

        String[] locals = list[0].split(separator);
        String[][] data = new String[list.length][locals.length];

        data[0] = locals;

        for (int i = 1; i < list.length; i++) data[i] = list[i].split(separator);

        SLang[] langs = new SLang[locals.length];
        for (int i = 0; i < locals.length; i++)
            if (!locals[i].equalsIgnoreCase("key"))
                langs[i] = new SLang(new Locale(locals[i]));


        for (int i = 1; i < data.length; i++)
            for (int j = 1; j < data[i].length; j++)
                langs[j].map.put(data[i][0], data[i][j]);


        SLang[] newLangs;
        if (langs[0] == null) {
            newLangs = new SLang[langs.length - 1];
            for (int i = 1; i < langs.length; i++) {
                newLangs[i - 1] = langs[i];
            }
        } else newLangs = langs;

        return newLangs;
    }
}
