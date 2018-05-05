package team.unstudio.udpl.i18n;

import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.config.EncodingDetect;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class PropertiesI18n extends BaseI18n {
    protected final Map<Locale, Properties> cache;

    public PropertiesI18n(@Nonnull Map<Locale, Properties> map) {
        this.cache = map;
    }

    @Override
    public String localize(Locale locale, String key) {
        if (cache.containsKey(locale))
            return cache.get(locale).getProperty(key, key);
        else if (cache.containsKey(getDefaultLocale()))
            return cache.get(getDefaultLocale()).getProperty(key, key);
        else if (getParent() != null)
            return getParent().localize(locale, key);
        else
            return key;
    }

    @SuppressWarnings("ConstantConditions")
    public static PropertiesI18n fromFile(@Nonnull File path) {
        if (!path.exists())
            throw new IllegalArgumentException("Path isn't exist.");
        if (!path.isDirectory())
            throw new IllegalArgumentException("Path isn't directory.");

        Map<Locale, Properties> map = new HashMap<>();

        for (File file : path.listFiles((file, name) -> name.endsWith(".lang.properties"))) {
            Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')).replaceAll("_", "-"));
            Properties properties = new Properties();
            try {
                properties.load(new InputStreamReader(new FileInputStream(file), EncodingDetect.getJavaEncode(file)));
            } catch (IOException e) {
                UDPLib.getLogger().error("Cannot read language file " + file.getPath(), e);
                continue;
            }
            map.put(locale, properties);
        }
        return new PropertiesI18n(map);
    }

    @SuppressWarnings("ConstantConditions")
    public static PropertiesI18n fromClassLoader(@Nonnull ClassLoader classLoader, @Nonnull String path) {
        Map<Locale, Properties> map = new HashMap<>();

        try {
            for (URL url : Collections.list(classLoader.getResources(path))) {
                File filePath = new File(url.toURI());
                if (!filePath.isDirectory())
                    throw new IllegalArgumentException("Path isn't directory.");

                for (File file : filePath.listFiles((file, name) -> name.endsWith(".properties"))) {
                    Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')).replaceAll("_", "-"));
                    Properties properties = new Properties();
                    properties.load(new InputStreamReader(new FileInputStream(file), EncodingDetect.getJavaEncode(file)));
                    map.put(locale, properties);
                }
            }
        } catch (Exception e) {
            UDPLib.getLogger().error("Cannot read language file from classloader, path " + path, e);
        }

        return new PropertiesI18n(map);
    }
}