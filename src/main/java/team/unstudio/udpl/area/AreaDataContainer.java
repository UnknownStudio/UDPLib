package team.unstudio.udpl.area;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

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

	@Override
	public Map<String, Object> serialize() {
		return this;
	}
}
