package team.unstudio.udplib.core.test;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import team.unstudio.udpl.config.ConfigItem;
import team.unstudio.udpl.config.SerializationHelper;

import java.util.Map;

public class TestSerialization implements ConfigurationSerializable{
	
	@ConfigItem("item")
	private int item;
	
	public TestSerialization(){}
	
	public TestSerialization(int item){
		this.item = item;
	}
	
	public void setItem(int item){
		this.item = item;
	}

	public int getItem(){
		return item;
	}
	
	@Override
	public Map<String, Object> serialize() {
		return SerializationHelper.serialize(this);
	}
	
	public static TestSerialization deserialize(Map<String, Object> data){
		return SerializationHelper.deserialize(new TestSerialization(), data);
	}

}
