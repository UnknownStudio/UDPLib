package team.unstudio.udpl.config.serialization;

import org.bukkit.configuration.ConfigurationSection;

import com.google.common.reflect.TypeToken;

public interface ConfigurationSerializer<T> {
	default public Class<?> getType(){
		return new TypeToken<T>(getClass()){}.getRawType();
	}
	
	@SuppressWarnings("unchecked")
	default public void serializeObject(ConfigurationSection section,Object obj){
		serialize(section, (T)obj);
	}
	
	public void serialize(ConfigurationSection section,T obj);
	
	public T deserialize(ConfigurationSection section);
}
