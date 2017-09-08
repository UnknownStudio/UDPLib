package team.unstudio.udpl.lang;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by trychen on 17/7/11.
 */
public class SLang {
    /**
     * 语言
     */
    public final Locale locale;

    /**
     * 数据
     */
    protected final Map<String, String> map = new HashMap<>();

    /**
     * 通过语言初始化一个 SLang
     * @param locale
     */
    public SLang(Locale locale) {
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
