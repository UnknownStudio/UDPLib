package team.unstudio.udpl.config.serialization;

import com.google.common.collect.Maps;
import org.bukkit.configuration.ConfigurationSection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public interface ConfigurationSerializationHelper{
	
	String CLASS_NAME_KEY = "===";
	
	Map<Class<?>,ConfigurationSerializer<?>> REGISTED_SERIALIZERS = Maps.newHashMap();
	
	public static void serialize(ConfigurationSection config,String key,List<Object> objs){
		config.set(key, objs.stream().map(ConfigurationSerializationHelper::serialize).collect(Collectors.toList()));
	}

	public static void serialize(ConfigurationSection config,String key,Object obj){
		config.set(key, serialize(obj));
	}

	static Object serialize(Object obj) {
		if (obj == null || isBasicType(obj.getClass())
				|| obj instanceof org.bukkit.configuration.serialization.ConfigurationSerializable) {
			return obj;
		} else if (hasSerializer(obj.getClass())) {
			Map<String, Object> map = Maps.newLinkedHashMap(getSerializer(obj.getClass()).serializeObject(obj));
			map.put(CLASS_NAME_KEY, obj.getClass().getName());
			return map;
		} else if (obj instanceof ConfigurationExternalizable) {
			Map<String, Object> map = Maps.newLinkedHashMap(((ConfigurationExternalizable) obj).serialize());
			map.put(CLASS_NAME_KEY, obj.getClass().getName());
			return map;
		} else if (obj instanceof ConfigurationSerializable) {
			Map<String, Object> map = Maps.newLinkedHashMap(serialize((ConfigurationSerializable) obj));
			map.put(CLASS_NAME_KEY, obj.getClass().getName());
			return map;
		} else {
			throw new SerializationException(obj.getClass().getName() + " can't serialize.");
		}
	}
	
	static Map<String,Object> serialize(ConfigurationSerializable obj){
		Class<?> clazz = obj.getClass();
		
		List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
		Class<?> parentClazz = clazz.getSuperclass();
		while(ConfigurationSerializable.class.isAssignableFrom(parentClazz)){
			Collections.addAll(declaredFields, clazz.getDeclaredFields());
			parentClazz = parentClazz.getSuperclass();
		}
		
		Map<String,Object> map = Maps.newLinkedHashMap();
		
		for(Field field:declaredFields){
			int modifiers = field.getModifiers();
			if(Modifier.isFinal(modifiers)||Modifier.isStatic(modifiers)||Modifier.isTransient(modifiers))
				continue;
			
			field.setAccessible(true);
			
			try {
				String key = field.getName();
				
				Setting setting = field.getAnnotation(Setting.class);
				if(setting!=null&&!setting.value().isEmpty())
					key = setting.value();
				
				map.put(key, serialize(field.get(obj)));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new SerializationException(obj.getClass().getName()+" can't serialize.",e);
			}
		}
		return map;
	}
	
	static List<Object> deserializeList(ConfigurationSection config,String key){
		return config.getList(key, Collections.emptyList()).stream().map(ConfigurationSerializationHelper::deserialize).collect(Collectors.toList());
	}
	
	static Optional<Object> deserialize(ConfigurationSection config,String key){
		return deserialize(config.get(key));
	}
	
	@SuppressWarnings("unchecked")
	static Optional<Object> deserialize(Object obj){
		if(obj instanceof Map){
			return deserialize((Map<String,Object>) obj);
		}else if(obj instanceof ConfigurationSection){
			return deserialize(((ConfigurationSection) obj).getValues(true));
		}else{
			return Optional.ofNullable(obj);
		}
	}
	
	static Optional<Object> deserialize(Map<String,Object> map){
		try {
			if(!map.containsKey(CLASS_NAME_KEY))
				return Optional.empty();
			
			Class<?> clazz = Class.forName((String) map.get(CLASS_NAME_KEY));
			map.remove(CLASS_NAME_KEY);
			if(hasSerializer(clazz))
				return Optional.ofNullable(getSerializer(clazz).deserialize(map));
			
			Constructor<?> constructor = clazz.getConstructor();
			constructor.setAccessible(true);
			Object obj = constructor.newInstance();
			if(obj instanceof ConfigurationExternalizable)
				((ConfigurationExternalizable) obj).deserialize(map);
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
						if(setting!=null&&!setting.value().isEmpty())
							key = setting.value();
						
						Optional<Object> value = deserialize(map.get(key));
						if(value.isPresent())
							field.set(obj, value.get());
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			return Optional.ofNullable(obj);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return Optional.empty();
	}
	
	AtomicReference<Set<Class<?>>> BASIC_TYPE_SET = new AtomicReference<>();
	
	static void initBasicType(){
		BASIC_TYPE_SET.set(new HashSet<>());
		Collections.addAll(BASIC_TYPE_SET.get(),
				String.class, 
				byte.class,Byte.class,
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
