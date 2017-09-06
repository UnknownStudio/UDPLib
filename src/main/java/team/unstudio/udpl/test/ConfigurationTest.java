package team.unstudio.udpl.test;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;

import team.unstudio.udpl.api.config.ConfigurationHelper;
import team.unstudio.udpl.api.config.serialization.ConfigurationSerializationHelper;

public class ConfigurationTest {
	
	private String name = getClass().getSimpleName();
	//private String fullname = getClass().getName();
	
	public static void main(String[] args) throws Exception{
		File file = new File("config.yml");
		YamlConfiguration config = ConfigurationHelper.loadConfiguration(file);
		ConfigurationSerializationHelper.serialize(config, "main", new ConfigurationTest());
		config.save(file);
	}

}
