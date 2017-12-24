package team.unstudio.udpl.i18n.slang;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by trychen on 17/7/11.
 */
public class CachedSLang {
    /**
     * 语言
     */
    public final Locale locale;

    /**
     * 数据
     */
    protected final Map<String, String> map = new HashMap<>();

    /**
     * 通过语言初始化一个 CachedSLang
     */
    public CachedSLang(Locale locale) {
        this.locale = locale;
    }

    /**
     * 获取本地化文本
     */
    public String get(String key){
        String s = map.get(key);
        if (s == null) return key;
        else return s;
    }
}
