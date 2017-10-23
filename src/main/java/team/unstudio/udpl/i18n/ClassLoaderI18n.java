package team.unstudio.udpl.i18n;

import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.configuration.Configuration;
import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.core.UDPLib;

import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * 从classpath中解析的国际化实现
 */
public class ClassLoaderI18n implements I18n {
    private final ClassLoader classLoader;
    private final String path;
    protected final Map<Locale,Configuration> cache = Maps.newHashMap();

    private Locale defaultLocale = Locale.getDefault();

    public ClassLoaderI18n(@Nonnull ClassLoader classLoader, @Nonnull String path) {
        Validate.notNull(classLoader, path);
        this.classLoader = classLoader;
        this.path = path;
        reload();
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

    public void reload(){
        cache.clear();
        try {
            for (URL url : Collections.list(classLoader.getResources(path))) {
                File filePath = new File(url.toURI());
                for (File file : (File[]) ArrayUtils.nullToEmpty(filePath.listFiles((file, name) -> name.endsWith(".yml")))) {
                    Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')).replaceAll("_", "-"));
                    Configuration config = ConfigurationHelper.loadConfiguration(file);
                    cache.put(locale, config);
                    UDPLib.getLog().info("Loaded language locale: " + locale.toLanguageTag());
                }
            }
        } catch (Exception e) {
            UDPLib.getLog().error("Cannot read language file from class path", e);
        }
    }

    @Override
    public String format(String key) {
        return format(defaultLocale, key);
    }

    @Override
    public String format(String key, Object... args){
        return format(defaultLocale, key, args);
    }

    @Override
    public String format(Locale locale, String key){
        if(cache.containsKey(locale))
            return cache.get(locale).getString(key, key);
        else if(cache.containsKey(defaultLocale))
            return cache.get(defaultLocale).getString(key, key);
        else
            return key;
    }
}
