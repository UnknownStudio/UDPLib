package team.unstudio.udpl.config;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;

import team.unstudio.udpl.config.serialization.ConfigurationSerializationHelper;

/**
 * 配置文件处理器
 */
public abstract class ConfigurationHandler{

	private final File file;
	private final Map<String,Object> defaults = new HashMap<>();
	private boolean loaded = false;
	
	public ConfigurationHandler(File file) {
		this.file = file;
	}
	
	/**
	 * 重载配置文件
	 * @return
	 */
	public boolean reload(){
		loadDefaultsIfBeforeNot();
			
		YamlConfiguration config = ConfigurationHelper.loadConfiguration(file);
		if(config == null)
			return false;

		for (Field f : getClass().getDeclaredFields()) {
			String key = getConfigItemKeyOrNull(f);
			if(key==null)continue;
			setFieldIfConfigValueExist(config,f,key);
		}

		return true;
	}
	
	private void loadDefaultsIfBeforeNot() {
		if(!loaded){
			loadDefaults();
			loaded=true;
		}
	}
	
	private void loadDefaults(){
		defaults.clear();
		for(Field f:getClass().getDeclaredFields()){
			String key = getConfigItemKeyOrNull(f);
			if(key==null)continue;
			putValue(key,f);
		}
	}
	
	private String getConfigItemKeyOrNull(Field f) {
		if(!isGeneral(f))return null;
		f.setAccessible(true);
		ConfigItem anno= f.getDeclaredAnnotation(ConfigItem.class);
		if(anno==null)return null;
		String key = anno.value().isEmpty()?f.getName():anno.value();
		return key;
	}
	
	private boolean isGeneral(Field f) {
		int modifiers = f.getModifiers();
		return !(Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers));
	}
	
	private void putValue(String key,Field f) {
		try {
			defaults.put(key,f.get(this));
		} catch (IllegalArgumentException | IllegalAccessException e) {}
	}
	
	private void setFieldIfConfigValueExist(YamlConfiguration config,Field f,String key) {
		try {
			f.set(this, ConfigurationSerializationHelper.deserialize(config, key).orElseGet(()->defaults.get(key)));
		} catch (IllegalArgumentException | IllegalAccessException e) {}
	}
	
	/**
	 * 保存配置文件
	 * @return
	 */
	public boolean save(){
		try{
			YamlConfiguration config = ConfigurationHelper.loadConfiguration(file);
			
			for(Field f:getClass().getDeclaredFields()){
				if(!isGeneral(f))
					continue;
				
				f.setAccessible(true);
				
				ConfigItem anno = f.getDeclaredAnnotation(ConfigItem.class);
				if(anno==null) 
					continue;
				
				String key = anno.value().isEmpty()?f.getName():anno.value();
				try {
					ConfigurationSerializationHelper.serialize(config, key, f.get(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					ConfigurationSerializationHelper.serialize(config, key, defaults.get(anno.value()));
				}
			}
			
			config.save(file);
			
			return true;
		}catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 注解一个配置项
	 * value为在配置文件中的key
	 * 可以为注解的配置项设置一个默认值
	 */
	@Retention(RUNTIME)
	@Target(FIELD)
	public static @interface ConfigItem{
		String value();
	}
}
