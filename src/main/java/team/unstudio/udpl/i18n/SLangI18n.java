package team.unstudio.udpl.i18n;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.configuration.Configuration;
import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.config.EncodingDetect;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.i18n.slang.CachedSLang;
import team.unstudio.udpl.i18n.slang.SLangSpliter;
import team.unstudio.udpl.util.FileUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class SLangI18n implements I18n {
    private String[] data;
    private String separator;

    protected final Map<Locale, CachedSLang> locale2SLang = Maps.newHashMap();
    private CachedSLang cachedSLang;

    private Locale defaultLocale = Locale.getDefault();

    protected SLangI18n(String[] data, String separator) {
        this.data = data;
        this.separator = separator;

        for (CachedSLang sLang : SLangSpliter.split(separator, data)) {
            locale2SLang.put(sLang.locale, sLang);
        }

        cachedSLang = locale2SLang.get(defaultLocale);
        if (cachedSLang == null) {
            cachedSLang = locale2SLang.values().stream().findFirst().get();
        }
    }
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }

    public void setDefaultLocale(String defaultLocale) {
        this.defaultLocale = Locale.forLanguageTag(defaultLocale);
    }

    @Override
    public String localize(String key) {
        return localize(defaultLocale, key);
    }

    @Override
    public String format(String key, Object... args){
        return format(defaultLocale, key, args);
    }

    @Override
    public String localize(Locale locale, String key){
        CachedSLang lang = locale == defaultLocale ? cachedSLang : locale2SLang.get(locale);
        if (lang == null) return key;

        return lang.get(key);
    }

    public static SLangI18n fromClassLoader(@Nonnull String separator, @Nonnull ClassLoader classLoader, @Nonnull String filePath){
        try {
            for (URL url : Collections.list(classLoader.getResources(filePath))) {
                File file = new File(url.toURI());
                if (file.isDirectory()) throw new IllegalArgumentException("Slang file must be a file, not a directory");
                return new SLangI18n(FileUtils.readFile2Array(file, EncodingDetect.getJavaEncode(file)), separator);
            }
        } catch (Exception e) {
            UDPLib.getLog().error("Cannot read language file from class path", e);
        }
        return null;
    }

    public static SLangI18n fromClassLoader(@Nonnull ClassLoader classLoader, @Nonnull String filePath){
        return fromClassLoader("\\|", classLoader, filePath);
    }
}
