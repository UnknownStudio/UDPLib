package team.unstudio.udpl.i18n;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.Configuration;
import com.google.common.collect.Maps;

import team.unstudio.udpl.config.ConfigurationHelper;

public class SimpleI18n implements I18n{
	
	public static final Locale DEFAULT_LOCALE = Locale.US;
	
	private final File path;
	protected final Map<Locale,Configuration> cache = Maps.newHashMap();
	
	private Locale defaultLocale = Locale.getDefault();

	public SimpleI18n(@Nonnull File path) {
		Validate.notNull(path);
		if(!path.exists())
			throw new IllegalArgumentException("Path isn't exist.");
		if(!path.isDirectory())
			throw new IllegalArgumentException("Path isn't directory.");
		
		this.path = path;
	}

	public final File getPath() {
		return path;
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
		for(File file:path.listFiles((file,name)->name.endsWith(".yml"))){
			Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')).replaceAll("_", "-"));
			Configuration config = ConfigurationHelper.loadConfiguration(file);
			cache.put(locale, config);
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
