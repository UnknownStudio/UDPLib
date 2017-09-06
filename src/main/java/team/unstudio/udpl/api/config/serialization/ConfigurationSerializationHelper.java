package team.unstudio.udpl.api.config.serialization;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import team.unstudio.udpl.api.config.serialization.ConfigurationSerializable;

import com.google.common.collect.Maps;

public final class ConfigurationSerializationHelper{
	
	public static final String CLASS_NAME_KEY = "===";
	
	private static final Map<Class<?>,ConfigurationSerializer<?>> REGISTED_SERIALIZERS = Maps.newHashMap();

	private ConfigurationSerializationHelper(){
		throw new AssertionError();
	}
	
	public static void serialize(ConfigurationSection config,String key,Object obj){
		if(obj == null)
			config.set(key, obj);
		else if(isBasicType(obj.getClass()))
			config.set(key, obj);
		else if(obj instanceof org.bukkit.configuration.serialization.ConfigurationSerializable)
			config.set(key, obj);
		else if(hasSerializer(obj.getClass())){
			ConfigurationSection section = config.createSection(key);
			getSerializer(obj.getClass()).serializeObject(section, obj);
			section.set(CLASS_NAME_KEY, obj.getClass().getName());
		}else if(obj instanceof ConfigurationExternalizable){
			ConfigurationSection section = config.createSection(key);
			((ConfigurationExternalizable) obj).serialize(section);
			section.set(CLASS_NAME_KEY, obj.getClass().getName());
		}else if(obj instanceof ConfigurationSerializable)
			serialize(config.createSection(key),(ConfigurationSerializable) obj);
		else 
			throw new SerializationException(obj.getClass().getName()+" can't serialize.");
	}
	
	public static void serialize(ConfigurationSection config,ConfigurationSerializable obj){
		Class<?> clazz = obj.getClass();
		config.set(CLASS_NAME_KEY, clazz.getName());
		
		List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
		Class<?> parentClazz = clazz.getSuperclass();
		while(ConfigurationSerializable.class.isAssignableFrom(parentClazz)){
			Collections.addAll(declaredFields, clazz.getDeclaredFields());
			parentClazz = parentClazz.getSuperclass();
		}
		
		for(Field field:declaredFields){
			int modifiers = field.getModifiers();
			if(Modifier.isFinal(modifiers)||Modifier.isStatic(modifiers)||Modifier.isTransient(modifiers))
				continue;
			
			field.setAccessible(true);
			
			try {
				String key = field.getName();
				
				Setting setting = field.getAnnotation(Setting.class);
				if(setting!=null)
					key = setting.value();
				
				serialize(config, key, field.get(obj));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Object deserialize(ConfigurationSection config,String key,Object def){
		Object result = deserialize(config, key);
		return result == null ? def : result;
	}
	
	public static Object deserialize(ConfigurationSection config,String key){
		if(!config.isConfigurationSection(key))
			return config.get(key);
		else if(config.contains(key+".=="))
			return config.get(key);
		else
			return deserialize(config.getConfigurationSection(key));
	}
	
	public static Object deserialize(ConfigurationSection config,Object def){
		Object result = deserialize(config);
		return result == null ? def : result;
	}
	
	public static Object deserialize(ConfigurationSection config){
		try {
			if(!config.contains(CLASS_NAME_KEY))
				return null;
			
			Class<?> clazz = Class.forName(config.getString(CLASS_NAME_KEY));
			if(hasSerializer(clazz))
				return getSerializer(clazz).deserialize(config);
			
			Object obj = clazz.newInstance();
			if(obj instanceof ConfigurationExternalizable)
				((ConfigurationExternalizable) obj).deserialize(config);
			else if(obj instanceof ConfigurationSerializable){
				List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
				Class<?> parentClazz = clazz.getSuperclass();
				while(ConfigurationSerializable.class.isAssignableFrom(parentClazz)){
					Collections.addAll(declaredFields, clazz.getDeclaredFields());
					parentClazz = parentClazz.getSuperclass();
				}
				
				for(Field field:declaredFields){
					int modifiers = field.getModifiers();
					if(Modifier.isFinal(modifiers)||Modifier.isStatic(modifiers)||Modifier.isTransient(modifiers))
						continue;
					
					field.setAccessible(true);
					
					try {
						String key = field.getName();
						
						Setting setting = field.getAnnotation(Setting.class);
						if(setting!=null)
							key = setting.value();
						
						field.set(obj, deserialize(config, key));
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			return obj;
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {}
		return null;
	}
	
	private static Set<Class<?>> BASIC_TYPE_SET;
	
	private static void initBasicType(){
		BASIC_TYPE_SET = new HashSet<>();
		Collections.addAll(BASIC_TYPE_SET, 
				String.class, 
				byte.class, Byte.class,
				short.class,Short.class,
				int.class,Integer.class,
				long.class,Long.class,
				float.class,Float.class,
				double.class,Double.class,
				boolean.class,Boolean.class);
	}
	
	public static boolean isBasicType(Class<?> clazz){
		if(BASIC_TYPE_SET == null)
			initBasicType();
		return BASIC_TYPE_SET.contains(clazz);
	}
	
	public static void registerSerializer(ConfigurationSerializer<?> serializer){
		REGISTED_SERIALIZERS.put(serializer.getType(), serializer);
	}
	
	public static void unregisterSerializer(ConfigurationSerializer<?> serializer){
		REGISTED_SERIALIZERS.remove(serializer.getType());
	}
	
	public static ConfigurationSerializer<?> getSerializer(Class<?> clazz){
		return REGISTED_SERIALIZERS.get(clazz);
	}
	
	public static boolean hasSerializer(Class<?> clazz){
		return REGISTED_SERIALIZERS.containsKey(clazz);
	}
}
