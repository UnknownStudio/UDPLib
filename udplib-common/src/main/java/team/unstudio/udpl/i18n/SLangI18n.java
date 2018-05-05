package team.unstudio.udpl.i18n;

import com.google.common.collect.Maps;
import team.unstudio.udpl.config.EncodingDetect;
import team.unstudio.udpl.i18n.slang.CachedSLang;
import team.unstudio.udpl.i18n.slang.SLangSpliter;
import team.unstudio.udpl.util.extra.FileUtils;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;

import java.io.File;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class SLangI18n extends BaseI18n {
    protected final Map<Locale, CachedSLang> locale2SLang = Maps.newHashMap();
    private CachedSLang cachedSLang;

    public SLangI18n(String[] data, String separator) {
        for (CachedSLang sLang : SLangSpliter.split(separator, data)) {
            locale2SLang.put(sLang.locale, sLang);
        }

        cachedSLang = locale2SLang.get(getDefaultLocale());
        if (cachedSLang == null) {
            cachedSLang = locale2SLang.values().stream().findFirst().get();
        }
    }

    @Override
    public String localize(Locale locale, String key) {
        CachedSLang lang = locale == getDefaultLocale() ? cachedSLang : locale2SLang.get(locale);
        if (lang == null){
        	if(getParent() != null)
    			return getParent().localize(locale, key);
        	else
        		return key;
        }

        return lang.get(key);
    }

    public static SLangI18n fromClassLoader(@Nonnull String separator, @Nonnull ClassLoader classLoader, @Nonnull String filePath) {
        try {
            URL url = classLoader.getResource(filePath);
            if (url == null) return null;

            return new SLangI18n(FileUtils.readFile2Array(url, EncodingDetect.getJavaEncode(url)), separator);
        } catch (Exception e) {
        	Bukkit.getLogger().log(Level.SEVERE, "Cannot read language file from class path", e);
        }
        return null;
    }

    public static SLangI18n fromFile(@Nonnull String separator, @Nonnull File file) {
        try {
            return new SLangI18n(FileUtils.readFile2Array(file, EncodingDetect.getJavaEncode(file)), separator);
        } catch (Exception e) {
        	Bukkit.getLogger().log(Level.SEVERE, "Cannot read language file from file", e);
        }
        return null;
    }

    /**
     * Create Slang with separator "\\|"
     * @param file file to create
     */
    public static SLangI18n fromFile(@Nonnull File file) {
        return fromFile("\\|", file);
    }

    public static SLangI18n fromClassLoader(@Nonnull ClassLoader classLoader, @Nonnull String filePath) {
        return fromClassLoader("\\|", classLoader, filePath);
    }
}
