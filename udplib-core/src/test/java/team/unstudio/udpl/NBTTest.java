package team.unstudio.udpl;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;

import org.junit.Test;
import static org.junit.Assert.*;

import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;
import team.unstudio.udpl.nms.nbt.NBTTagList;
import team.unstudio.udpl.nms.nbt.NBTTagString;
import team.unstudio.udpl.nms.nbt.NBTUtils;

public class NBTTest {
	@Test
	public void nbt() throws IOException {
		NBTUtils.registerAllNBTSerilizable();
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		nbtTagCompound.setByte("byte", Byte.MAX_VALUE);
		nbtTagCompound.setByteArray("byteArray", new byte[]{Byte.MAX_VALUE,Byte.MIN_VALUE});
		nbtTagCompound.setDouble("double", Double.MAX_VALUE);
		nbtTagCompound.setFloat("float", Float.MAX_VALUE);
		nbtTagCompound.setInt("int", Integer.MAX_VALUE);
		nbtTagCompound.setIntArray("intArray", new int[]{Integer.MAX_VALUE,Integer.MIN_VALUE});
		nbtTagCompound.setLong("long", Long.MAX_VALUE);
		nbtTagCompound.setShort("short", Short.MAX_VALUE);
		nbtTagCompound.setString("string", "String");
		nbtTagCompound.set("list", new NBTTagList(new NBTTagString("String")));
		String expected = "{\"byte\":127B,\"byteArray\":[B;127,-128],\"double\":1.7976931348623157E308D,\"float\":3.4028235E38F,\"int\":2147483647,\"intArray\":[I;2147483647,-2147483648],\"long\":9223372036854775807L,\"short\":32767S,\"string\":\"String\",\"list\":[\"String\"]}";
		assertEquals(expected, nbtTagCompound.toString());

		NBTTagCompound deserializedNbt = (NBTTagCompound) NBTUtils.parseFromJson(nbtTagCompound.toString());
		assertEquals(expected, deserializedNbt.toString());

		File file = new File("nbt.yml");
		FileConfiguration config = ConfigurationHelper.loadConfiguration(file);
		file.deleteOnExit();

		config.set("nbt", nbtTagCompound);
		config.save(file);
		config = ConfigurationHelper.loadConfiguration(file);
		assertEquals(expected, config.get("nbt").toString());
	}

}
