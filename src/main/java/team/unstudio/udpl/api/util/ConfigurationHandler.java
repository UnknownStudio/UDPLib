package team.unstudio.udpl.api.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

/**
 * 配置文件处理器
 */
public abstract class ConfigurationHandler{

	private final File file;
	private final Map<String,Object> defaults = new HashMap<>();
	
	public ConfigurationHandler(File file) {
		this.file = file;
		loadDefaults();
	}
	
	private void loadDefaults(){
		defaults.clear();
		
		for(Field f:getClass().getDeclaredFields()){
			f.setAccessible(true);
			
			ConfigItem anno;
			if((anno=f.getAnnotation(ConfigItem.class))==null) continue;
			
			try {
				defaults.put(anno.value(),f.get(this));
			} catch (IllegalArgumentException | IllegalAccessException e) {}
		}
	}
	
	/**
	 * 重载配置文件
	 * @return
	 */
	public boolean reload(){
		try{
			if(!file.getParentFile().exists())file.getParentFile().mkdirs();
			
			if(!file.exists()) file.createNewFile();
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(Field f:getClass().getDeclaredFields()){
				f.setAccessible(true);
				
				ConfigItem anno;
				if((anno=f.getAnnotation(ConfigItem.class))==null) continue;
				
				try {
					f.set(this,config.get(anno.value(), defaults.get(anno.value())));
				} catch (IllegalArgumentException | IllegalAccessException e) {}
			}
			
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	/**
	 * 保存配置文件
	 * @return
	 */
	public boolean save(){
		try{
			if(!file.getParentFile().exists())file.getParentFile().mkdirs();
			
			if(!file.exists()) file.createNewFile();
			
			YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
			
			for(Field f:getClass().getDeclaredFields()){
				f.setAccessible(true);
				
				ConfigItem anno;
				if((anno=f.getAnnotation(ConfigItem.class))==null) continue;
				
				try {
					config.set(anno.value(), f.get(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {}
			}
			
			config.save(file);
			
			return true;
		}catch(IOException e){
			return false;
		}
	}
	
	/**
	 * 注解一个配置项
	 * value为在配置文件中的key
	 * 可以为注解的配置项设置一个默认值
	 */
	public @interface ConfigItem{
		String value();
	}
}
