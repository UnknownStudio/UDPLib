package team.unstudio.udpl;

import java.io.File;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.config.serialization.ConfigurationExternalizable;
import team.unstudio.udpl.config.serialization.ConfigurationSerializable;
import team.unstudio.udpl.config.serialization.ConfigurationSerializationHelper;
import team.unstudio.udpl.config.serialization.ConfigurationSerializer;
import team.unstudio.udpl.config.serialization.Setting;

public class ConfigurationTest {
	
	public static void main(String[] args) throws Exception{
		ConfigurationSerializationHelper.registerSerializer(new SerializerNameSerializer());
		
		File file = new File("config.yml");
		YamlConfiguration config = ConfigurationHelper.loadConfiguration(file);
		ConfigurationSerializationHelper.serialize(config, "SerializableName", new SerializableName());
		ConfigurationSerializationHelper.serialize(config, "ExternalizableName", new ExternalizableName());
		ConfigurationSerializationHelper.serialize(config, "SerializerName", new SerializerName("SerializerName"));
		config.save(file);
		
		config = ConfigurationHelper.loadConfiguration(file);
		System.out.println(((SerializableName)(ConfigurationSerializationHelper.deserialize(config,"SerializableName").get())).name);
		System.out.println(((ExternalizableName)(ConfigurationSerializationHelper.deserialize(config,"ExternalizableName").get())).name);
		System.out.println(((SerializerName)(ConfigurationSerializationHelper.deserialize(config,"SerializerName").get())).name);
	}
	
	public static class SerializableName implements ConfigurationSerializable{
		@Setting("className")
		private String name = getClass().getSimpleName();
	}
	
	public static class ExternalizableName implements ConfigurationExternalizable{
		private String name = getClass().getSimpleName();

		@Override
		public void serialize(ConfigurationSection section) {
			section.set("name", name);
		}

		@Override
		public void deserialize(ConfigurationSection section) {
			name = section.getString("name");
		}
	}
	
	public static class SerializerName{
		private final String name;
		public SerializerName(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	public static class SerializerNameSerializer implements ConfigurationSerializer<SerializerName>{

		@Override
		public void serialize(ConfigurationSection section, SerializerName obj) {
			section.set("name", obj.getName());
		}

		@Override
		public SerializerName deserialize(ConfigurationSection section) {
			return new SerializerName(section.getString("name"));
		}
		
	}
}
