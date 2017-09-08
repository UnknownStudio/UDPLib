package team.unstudio.udpl.util;

import java.io.File;
import java.util.Locale;

import org.bukkit.configuration.Configuration;
import team.unstudio.udpl.config.ConfigurationHelper;

public class I18n {
	
	public static final Locale LOCAL_LOCALE = Locale.getDefault();
	public static final Locale DEFAULT_LOCALE = Locale.US;
	
	private final File path;
	private Locale locale = LOCAL_LOCALE;
	private Configuration cache = null;
	
	public I18n(File path) {
		if(path==null)
			throw new IllegalArgumentException("Path can't Null.");
		if(!path.exists())
			throw new IllegalArgumentException("Path isn't exist.");
		if(!path.isDirectory())
			throw new IllegalArgumentException("Path isn't directory.");
		
		this.path = path;
	}

	public File getPath() {
		return path;
	}
	
	public Locale getSecretLanguage(){
		return locale;
	}
	
	public String getSecretLanguageTag(){
		return locale.toLanguageTag();
	}
	
	public void setSecretLanguage(String language) {
		locale = Locale.forLanguageTag(language); 
		reload();
	}
	
	public void setSecretLanguage(Locale language) {
		locale = language; 
		reload();
	}
	
	private void reload(){
		File file = new File(path, getSecretLanguageTag()+".yml");
		if(file.exists())
			cache = ConfigurationHelper.loadConfiguration(file);
		else
			loadDefault();
	}
	
	private void loadDefault(){
		File defaultFile = new File(path, DEFAULT_LOCALE.toLanguageTag()+".yml");
		if(defaultFile.exists())
			cache = ConfigurationHelper.loadConfiguration(defaultFile);
		else
			cache = null;
	}
	
	public String format(String key, Object... args){
		if(cache == null)
			return String.format(key, args);
		else
			return String.format(cache.getString(key, key), args);
	}
}
