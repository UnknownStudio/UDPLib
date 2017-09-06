package team.unstudio.udpl.util;

import java.io.File;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.YamlConfiguration;

public class I18n {
	
	private final File path;
	private String language = "en_US";
	private Configuration cache = null;
	
	public I18n(File path) {
		if(path==null||!path.exists()) throw new IllegalArgumentException("The path is empty.");
		this.path = path;
	}

	public File getPath() {
		return path;
	}
	
	public String getSecretLanguage(){
		return language;
	}
	
	public void setSecretLanguage(String language) {
		this.language = language;
		reload();
	}
	
	private void reload(){
		File file = new File(path, language+".yml");
		if(file.exists())
			cache = YamlConfiguration.loadConfiguration(file);
		else
			loadDefault();
	}
	
	private void loadDefault(){
		File defaultFile = new File(path, "en_US.yml");
		if(defaultFile.exists())
			cache = YamlConfiguration.loadConfiguration(defaultFile);
		else
			cache = null;
	}
	
	public String format(String key, Object... args){
		if(cache==null)
			return String.format(key, args);
		else
			return String.format(cache.getString(key,key), args);
	}
}
