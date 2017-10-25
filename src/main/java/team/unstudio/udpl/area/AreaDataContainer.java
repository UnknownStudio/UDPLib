package team.unstudio.udpl.area;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AreaDataContainer extends HashMap<String, Object> implements ConfigurationSerializable{
	
	public AreaDataContainer() {}
	
	public AreaDataContainer(Map<String, Object> data) {
		super(data);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key){
		try{
			return (T) super.get(key);
		}catch(ClassCastException e){
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key,T def){
		if(!containsKey(key))
			return def;
		try{
			return (T) super.get(key);
		}catch(ClassCastException e){
			return def;
		}
	}
	
	@Override
	public Object put(String key, Object value){
		if(!checkKey(key))
			throw new IllegalArgumentException("Wrong key pattern");
		return super.put(key, value);
	}
	
	private static final Pattern KEY_PATTERN = Pattern.compile("[A-Z|a-z|0-9|_|$]+");
	private boolean checkKey(String key){
		return KEY_PATTERN.matcher(key).matches();
	}

	@Override
	public Map<String, Object> serialize() {
		put("==", getClass().getName());
		return this;
	}
}
