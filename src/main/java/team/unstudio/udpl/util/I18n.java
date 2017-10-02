package team.unstudio.udpl.util;

import java.io.File;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;

import team.unstudio.udpl.config.ConfigurationHelper;

public class I18n {
	
	public static final Locale LOCAL_LOCALE = Locale.getDefault();
	public static final Locale DEFAULT_LOCALE = Locale.US;
	
	private final File path;
	private final Map<Locale,Configuration> cache = Maps.newHashMap();
	
	private Locale defaultLocale = LOCAL_LOCALE;

	public I18n(@Nonnull File path) {
		Validate.notNull(path);
		if(!path.exists())
			throw new IllegalArgumentException("Path isn't exist.");
		if(!path.isDirectory())
			throw new IllegalArgumentException("Path isn't directory.");
		
		this.path = path;
	}

	public File getPath() {
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
			Locale locale = Locale.forLanguageTag(file.getName().substring(0, file.getName().lastIndexOf('.')));
			Configuration config = ConfigurationHelper.loadConfiguration(file);
			cache.put(locale, config);
		}
	}
	
	public String format(String key, Object... args){
		return format(defaultLocale, key, args);
	}
	
	public String format(String locale, String key, Object... args){
		return format(Locale.forLanguageTag(locale), key, args);
	}
	
	public String format(Locale locale, String key, Object... args){
		if(cache.containsKey(locale))
			return String.format(cache.get(locale).getString(key, key), args);
		else if(cache.containsKey(defaultLocale))
			return String.format(cache.get(defaultLocale).getString(key, key), args);
		else if(cache.containsKey(DEFAULT_LOCALE))
			return String.format(cache.get(DEFAULT_LOCALE).getString(key, key), args);
		else
			return String.format(key, args);
	}
	
	public void sendMessage(Player player, String locale, String key, Object... args){
		player.sendMessage(format(locale, key, args));
	}
	
	public void sendMessage(Player player, Locale locale, String key, Object... args){
		player.sendMessage(format(locale, key, args));
	}
	
	public void sendMessage(Player player, String key, Object... args){
		player.sendMessage(format(defaultLocale, key, args));
	}
	
	public void sendLocalizedMessage(Player player, String key, Object... args){
		player.sendMessage(format(PlayerUtils.getLanguageLocale(player), key, args));
	}
}
