package team.unstudio.udpl.config.serialization;

import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public interface ConfigurationSerializationHelper{
	
	String CLASS_NAME_KEY = "===";
	
	Map<Class<?>,ConfigurationSerializer<?>> REGISTED_SERIALIZERS = Maps.newHashMap();
	
	static void serialize(ConfigurationSection config, String key, Object obj){
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
	
	static void serialize(ConfigurationSection config, ConfigurationSerializable obj){
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
				throw new SerializationException(obj.getClass().getName()+" can't serialize.",e);
			}
		}
	}
	
	static Optional<Object> deserialize(ConfigurationSection config, String key){
		if(!config.isConfigurationSection(key))
			return Optional.ofNullable(config.get(key));
		else if(config.contains(key+".=="))
			return Optional.ofNullable(config.get(key));
		else
			return deserialize(config.getConfigurationSection(key));
	}
	
	static Optional<Object> deserialize(ConfigurationSection config){
		try {
			if(!config.contains(CLASS_NAME_KEY))
				return Optional.empty();
			
			Class<?> clazz = Class.forName(config.getString(CLASS_NAME_KEY));
			if(hasSerializer(clazz))
				return Optional.ofNullable(getSerializer(clazz).deserialize(config));
			
			Constructor<?> constructor = clazz.getConstructor();
			constructor.setAccessible(true);
			Object obj = constructor.newInstance();
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
						
						Optional<Object> value = deserialize(config, key);
						if(value.isPresent())
							field.set(obj, value.get());
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			return Optional.ofNullable(obj);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {}
		return Optional.empty();
	}
	
	AtomicReference<Set<Class<?>>> BASIC_TYPE_SET = new AtomicReference<>();
	
	static void initBasicType(){
		BASIC_TYPE_SET.set(new HashSet<>());
		Collections.addAll(BASIC_TYPE_SET.get(),
				String.class, 
				byte.class, Byte.class,
				short.class,Short.class,
				int.class,Integer.class,
				long.class,Long.class,
				float.class,Float.class,
				double.class,Double.class,
				boolean.class,Boolean.class);
	}
	
	static boolean isBasicType(Class<?> clazz){
		if(BASIC_TYPE_SET.get() == null)
			initBasicType();
		return BASIC_TYPE_SET.get().contains(clazz);
	}
	
	static void registerSerializer(ConfigurationSerializer<?> serializer){
		REGISTED_SERIALIZERS.put(serializer.getType(), serializer);
	}
	
	static void unregisterSerializer(ConfigurationSerializer<?> serializer){
		REGISTED_SERIALIZERS.remove(serializer.getType());
	}
	
	static ConfigurationSerializer<?> getSerializer(Class<?> clazz){
		return REGISTED_SERIALIZERS.get(clazz);
	}
	
	static boolean hasSerializer(Class<?> clazz){
		return REGISTED_SERIALIZERS.containsKey(clazz);
	}
}
