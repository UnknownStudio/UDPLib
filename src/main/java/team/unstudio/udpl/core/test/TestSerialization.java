package team.unstudio.udpl.core.test;

import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import team.unstudio.udpl.config.ConfigItem;
import team.unstudio.udpl.config.SerializationHelper;

public class TestSerialization implements ConfigurationSerializable{
	
	@ConfigItem("item")
	private int item = 233;

	@Override
	public Map<String, Object> serialize() {
		return SerializationHelper.serialize(this);
	}
	
	public static TestSerialization deserialize(Map<String, Object> data){
		return SerializationHelper.deserialize(new TestSerialization(), data);
	}

}
