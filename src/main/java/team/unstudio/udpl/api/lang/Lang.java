package team.unstudio.udpl.api.lang;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by trychen on 17/7/11.
 */
public class Lang {
    /**
     * 语言
     */
    public final Locale locale;

    /**
     * 数据
     */
    public final Map<String, String> map = new HashMap<>();

    /**
     * 通过语言初始化一个 Lang
     * @param locale
     */
    public Lang(Locale locale) {
        this.locale = locale;
    }

    /**
     * 获取本地化文本
     * @param key
     * @return
     */
    public String get(String key){
        return map.get(key);
    }
}
