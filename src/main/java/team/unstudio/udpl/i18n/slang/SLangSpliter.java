package team.unstudio.udpl.i18n.slang;

import java.util.Locale;

/**
 * Created by trychen on 17/7/11.
 */
public interface SLangSpliter {
    /**
     * 分隔符
     */
    String separator = "|";

    /**
     * 使用默认的分割符号分割文本到 CachedSLang
     *
     * @param list 要分割的文本
     */
    static CachedSLang[] split(String[] list) {
        return split(separator, list);
    }

    /**
     * 分割文本到 CachedSLang
     *
     * @param separator 分割符 (Regex)
     * @param list      文本行数据
     */
    static CachedSLang[] split(String separator, String[] list) {
        if (list.length < 0) return new CachedSLang[0];

        String[] locals = list[0].split(separator);
        String[][] data = new String[list.length][locals.length];

        data[0] = locals;

        for (int i = 1; i < list.length; i++) data[i] = list[i].split(separator);

        // get the head key or locale
        CachedSLang[] langs = new CachedSLang[locals.length];
        for (int i = 0; i < locals.length; i++)
            if (!locals[i].equalsIgnoreCase("key"))
                langs[i] = new CachedSLang(Locale.forLanguageTag(locals[i].replaceAll("_", "-")));


        for (int i = 1; i < data.length; i++)
            for (int j = 1; j < data[i].length; j++)
                langs[j].map.put(data[i][0], data[i][j]);


        CachedSLang[] newLangs;
        if (langs[0] == null) {
            newLangs = new CachedSLang[langs.length - 1];
            for (int i = 1; i < langs.length; i++) {
                newLangs[i - 1] = langs[i];
            }
        } else newLangs = langs;

        return newLangs;
    }
}
