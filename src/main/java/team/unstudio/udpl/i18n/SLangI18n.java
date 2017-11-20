package team.unstudio.udpl.i18n;

import com.google.common.collect.Maps;
import team.unstudio.udpl.config.EncodingDetect;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.i18n.slang.CachedSLang;
import team.unstudio.udpl.i18n.slang.SLangSpliter;
import team.unstudio.udpl.util.FileUtils;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.util.*;

public class SLangI18n implements I18n {
    private String[] data;
    private String separator;

    protected final Map<Locale, CachedSLang> locale2SLang = Maps.newHashMap();
    private CachedSLang cachedSLang;

    private Locale defaultLocale = Locale.getDefault();
	private I18n parent;
	
	@Override
	public I18n getParent() {
		return parent;
	}

	@Override
	public void setParent(I18n parent) {
		this.parent = parent;
	}

    public SLangI18n(String[] data, String separator) {
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
    public String format(String key, Object... args) {
        return format(defaultLocale, key, args);
    }

    @Override
    public String localize(Locale locale, String key) {
        CachedSLang lang = locale == defaultLocale ? cachedSLang : locale2SLang.get(locale);
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
            UDPLib.getLog().error("Cannot read language file from class path", e);
        }
        return null;
    }

    public static SLangI18n fromFile(@Nonnull String separator, @Nonnull File file) {
        try {
            return new SLangI18n(FileUtils.readFile2Array(file, EncodingDetect.getJavaEncode(file)), separator);
        } catch (Exception e) {
            UDPLib.getLog().error("Cannot read language file from file", e);
        }
        return null;
    }

    /**
     * Create Slang with separator "\\|"
     * @param file
     * @return
     */
    public static SLangI18n fromFile(@Nonnull File file) {
        return fromFile("\\|", file);
    }

    public static SLangI18n fromClassLoader(@Nonnull ClassLoader classLoader, @Nonnull String filePath) {
        return fromClassLoader("\\|", classLoader, filePath);
    }
}
