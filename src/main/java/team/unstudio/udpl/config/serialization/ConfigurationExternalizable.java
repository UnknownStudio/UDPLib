package team.unstudio.udpl.config.serialization;

import java.util.Map;

public interface ConfigurationExternalizable extends ConfigurationSerializable{

	public Map<String,Object> serialize();
	
	public void deserialize(Map<String,Object> map);
}
