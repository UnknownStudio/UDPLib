package team.unstudio.udpl;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import org.junit.*;

import com.google.common.collect.Maps;

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
		public Map<String, Object> serialize() {
			Map<String, Object> map = Maps.newLinkedHashMap();
			map.put("name", name);
			return map;
		}

		@Override
		public void deserialize(Map<String, Object> map) {
			name = (String) map.get("name");
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
		public Map<String, Object> serialize(SerializerName obj) {
			Map<String,Object> map = Maps.newLinkedHashMap();
			map.put("name", obj.getName());
			return map;
		}

		@Override
		public SerializerName deserialize(Map<String, Object> map) {
			return new SerializerName((String) map.get("name"));
		}
		
	}
}
