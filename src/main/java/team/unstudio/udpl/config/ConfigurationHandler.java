package team.unstudio.udpl.config;

import com.google.common.base.Strings;
import org.bukkit.configuration.file.YamlConfiguration;
import team.unstudio.udpl.core.UDPLib;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置文件处理器。
 *
 * 必须要调用一次 reload() 才会加载相关内容。
 * 相关使用方法请查看 <a href="https://github.com/UnknownStudio/UDPLib/wiki/%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6API-(Configuration-API)">Github Wiki</a>
 */
public abstract class ConfigurationHandler{
    /**
     * 配置文件路径
     */
	private final File file;

    /**
     * 配置项默认值，通过 {@link ConfigurationHandler#loadDefaults()} 加载
     */
	private final Map<String,Object> defaults = new HashMap<>();

    /**
     * 是否已经加载过配置项默认值
     */
	private boolean loaded = false;
	
	public ConfigurationHandler(File file) {
		this.file = file;
	}
	
	/**
	 * 重载配置文件
	 *
	 * @return 是否加载成功
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

    /**
     * 检查是否已经加载过 ({@link ConfigurationHandler#loaded})，否则加载配置项默认值
     */
	private void loadDefaultsIfBeforeNot() {
		if(!loaded){
			loadDefaults();
			loaded = true;
		}
	}

    /**
     * 加载配置项默认值，并保存到 {@link ConfigurationHandler#defaults}
     */
	private void loadDefaults(){
		defaults.clear();
		for(Field f:getClass().getDeclaredFields()){
			String key = getConfigItemKeyOrNull(f);
			if(key == null) continue;
			putValue(key,f);
		}
	}

	@Nullable
	private String getConfigItemKeyOrNull(Field f) {
		if(!isGeneral(f))return null;
		f.setAccessible(true);
		ConfigItem anno= f.getDeclaredAnnotation(ConfigItem.class);
		if(anno==null)return null;
        return anno.value().isEmpty()?f.getName():anno.value();
	}
	
	private boolean isGeneral(Field f) {
		int modifiers = f.getModifiers();
		return !(Modifier.isFinal(modifiers) || Modifier.isStatic(modifiers) || Modifier.isTransient(modifiers));
	}

    /**
     * 设置配置项 Field 的值
     */
	private void putValue(String key,Field f) {
		try {
			defaults.put(key,f.get(this));
		} catch (IllegalArgumentException | IllegalAccessException ignored) {}
	}

    /**
     * 在配置文件中存在 key 时，设置 Field 的值为配置文件中的值
     */
	private void setFieldIfConfigValueExist(YamlConfiguration config,Field f,String key) {
		try {
			f.set(this, config.get(key,defaults.get(key)));
		} catch (IllegalArgumentException | IllegalAccessException ignored) {}
	}
	
	/**
	 * 保存配置文件
	 * @return 是否加载成功
	 */
	public boolean save(){
		try{
			YamlConfiguration config = ConfigurationHelper.loadConfiguration(file);

			if (config == null)
				return false;
			
			for(Field f:getClass().getDeclaredFields()){
				if(!isGeneral(f))
					continue;
				
				f.setAccessible(true);
				
				ConfigItem anno = f.getDeclaredAnnotation(ConfigItem.class);
				if(anno == null) 
					continue;
				
				String key = Strings.isNullOrEmpty(anno.value())?f.getName():anno.value();
				try {
					config.set(key, f.get(this));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					config.set(key, defaults.get(anno.value()));
				}
			}
			
			config.save(file);
			
			return true;
		}catch(IOException e){
			UDPLib.getLog().error(e);
			return false;
		}
	}
}
