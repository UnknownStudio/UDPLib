package team.unstudio.udpl.config.serialization;

import java.util.Map;

import com.google.common.reflect.TypeToken;

public interface ConfigurationSerializer<T> {
	default public Class<?> getType(){
		return new TypeToken<T>(getClass()){}.getRawType();
	}
	
	@SuppressWarnings("unchecked")
	default public Map<String,Object> serializeObject(Object obj){
		return serialize((T)obj);
	}
	
	public Map<String,Object> serialize(T obj);
	
	public T deserialize(Map<String,Object> map);
}
