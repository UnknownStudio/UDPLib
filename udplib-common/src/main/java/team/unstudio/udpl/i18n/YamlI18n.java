package team.unstudio.udpl.i18n;

import org.apache.commons.lang3.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.config.ConfigurationHelper;
import javax.annotation.Nonnull;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;

public class YamlI18n extends BaseI18n {
	protected final Map<Locale,Configuration> cache;

	public YamlI18n(@Nonnull Map<Locale,Configuration> map) {
		this.cache = map;
	}

	@Override
	public String localize(Locale locale, String key){
		if(cache.containsKey(locale))
			return cache.get(locale).getString(key, key);
		else if(cache.containsKey(getDefaultLocale()))
			return cache.get(getDefaultLocale()).getString(key, key);
		else if(getParent() != null)
			return getParent().localize(locale, key);
		else
			return key;
	}

	@SuppressWarnings("ConstantConditions")
	public static YamlI18n fromFile(@Nonnull File path) {
		if(!path.exists())
			throw new IllegalArgumentException("Path isn't exist.");
		if(!path.isDirectory())
			throw new IllegalArgumentException("Path isn't directory.");

		Map<Locale,Configuration> map = new HashMap<>();

		for(File file:path.listFiles((file,name)->name.endsWith(".yml"))){
			Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')).replaceAll("_", "-"));
			Configuration config = ConfigurationHelper.loadConfiguration(file);
			if(config != null)
				map.put(locale, config);
		}
		return new YamlI18n(map);
	}

	public static YamlI18n fromClassLoader(@Nonnull ClassLoader classLoader, @Nonnull String path) {
		Map<Locale,Configuration> map = new HashMap<>();

		try {
			for (URL url : Collections.list(classLoader.getResources(path))) {
				File filePath = new File(url.toURI());
				for (File file : (File[]) ArrayUtils.nullToEmpty(filePath.listFiles((file, name) -> name.endsWith(".yml")))) {
					Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')).replaceAll("_", "-"));
					Configuration config = ConfigurationHelper.loadConfiguration(file);
					if(config != null)
						map.put(locale, config);
				}
			}
		} catch (Exception e) {
			UDPLib.getLogger().error("Cannot read language file from class path", e);
		}

		return new YamlI18n(map);
	}
}
