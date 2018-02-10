package team.unstudio.udpl.config;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

public interface SerializationHelper {

	static Map<String,Object> serialize(@Nullable ConfigurationSerializable obj){
		Validate.notNull(obj);
		
		Map<String,Object> map = Maps.newLinkedHashMap();
		Class<?> clazz = obj.getClass();
		while(ConfigurationSerializable.class.isAssignableFrom(clazz)){
			for(Field field:clazz.getDeclaredFields()){
				int modifiers = field.getModifiers();
				if(Modifier.isFinal(modifiers)||Modifier.isStatic(modifiers)||Modifier.isTransient(modifiers))
					continue;
				
				field.setAccessible(true);
				
				String key = field.getName();
				
				ConfigItem setting = field.getAnnotation(ConfigItem.class);
				if(setting != null && !Strings.isNullOrEmpty(key))
					key = setting.value();
				
				try {
					map.put(key, field.get(obj));
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new SerializationException("Serialize failed.",e);
				}
			}
			clazz = clazz.getSuperclass();
		}
		
		return map;
	}
	
	static <T extends ConfigurationSerializable> T deserialize(T obj, Map<String,Object> map){
		Validate.notNull(obj);
		Validate.notNull(map);
		
		Class<?> clazz = obj.getClass();
		while(ConfigurationSerializable.class.isAssignableFrom(clazz)){
			for(Field field:clazz.getDeclaredFields()){
				int modifiers = field.getModifiers();
				if(Modifier.isFinal(modifiers)||Modifier.isStatic(modifiers)||Modifier.isTransient(modifiers))
					continue;
				
				field.setAccessible(true);
				
				String key = field.getName();
				
				ConfigItem setting = field.getAnnotation(ConfigItem.class);
				if(setting != null && !Strings.isNullOrEmpty(key))
					key = setting.value();
				
				if(map.containsKey(key)){
					try {
						field.set(obj, map.get(key));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new SerializationException("Deserialize failed.",e);
					}
				}
			}
			clazz = clazz.getSuperclass();
		}
		
		return obj;
	}
}
