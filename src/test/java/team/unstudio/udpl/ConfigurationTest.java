package team.unstudio.udpl;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import org.junit.*;
import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.config.serialization.ConfigurationExternalizable;
import team.unstudio.udpl.config.serialization.ConfigurationSerializable;
import team.unstudio.udpl.config.serialization.ConfigurationSerializationHelper;
import team.unstudio.udpl.config.serialization.ConfigurationSerializer;
import team.unstudio.udpl.config.serialization.Setting;

import static org.junit.Assert.*;

public class ConfigurationTest {
	private static File file = new File("config_tester.yml");

	@BeforeClass
	public static void register() throws IOException {
		ConfigurationSerializationHelper.registerSerializer(new SerializerNameSerializer());
		file.createNewFile();
	}

	@Test
	public void saveAndLoad() throws Exception {
		YamlConfiguration config = ConfigurationHelper.newConfiguration();
		ConfigurationSerializationHelper.serialize(config, "SerializableName", new SerializableName());
		ConfigurationSerializationHelper.serialize(config, "ExternalizableName", new ExternalizableName());
		ConfigurationSerializationHelper.serialize(config, "SerializerName", new SerializerName("SerializerName"));
		config.save(file);

		config = ConfigurationHelper.loadConfiguration(file);
		assertEquals("SerializableName", ((SerializableName)(ConfigurationSerializationHelper.deserialize(config,"SerializableName").get())).name);
		assertEquals("ExternalizableName", ((ExternalizableName)(ConfigurationSerializationHelper.deserialize(config,"ExternalizableName").get())).name);
		assertEquals("SerializerName", ((SerializerName)(ConfigurationSerializationHelper.deserialize(config,"SerializerName").get())).name);

	}
	@AfterClass
	public static void delFile(){
		file.delete();
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
	
	public static class SerializerNameSerializer implements ConfigurationSerializer<SerializerName> {
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
