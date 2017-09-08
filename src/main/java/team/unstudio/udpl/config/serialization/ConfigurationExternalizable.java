package team.unstudio.udpl.config.serialization;

import org.bukkit.configuration.ConfigurationSection;

public interface ConfigurationExternalizable extends ConfigurationSerializable{

	public void serialize(ConfigurationSection section);
	
	public void deserialize(ConfigurationSection section);
}
