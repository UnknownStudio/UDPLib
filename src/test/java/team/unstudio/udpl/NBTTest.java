package team.unstudio.udpl;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;

import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;
import team.unstudio.udpl.nms.nbt.NBTTagList;
import team.unstudio.udpl.nms.nbt.NBTTagString;
import team.unstudio.udpl.nms.nbt.NBTUtils;

public class NBTTest {

	public static void main(String[] args) throws IOException {
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
		System.out.println(nbtTagCompound.toString());
		NBTTagCompound deserializedNbt = (NBTTagCompound) NBTUtils.parseFromJson(nbtTagCompound.toString());
		System.out.println(deserializedNbt.toString());
		File file = new File("nbt.yml");
		FileConfiguration config = ConfigurationHelper.loadConfiguration(file);
		config.set("nbt", nbtTagCompound);
		config.save(file);
		config = ConfigurationHelper.loadConfiguration(file);
		System.out.println(config.get("nbt"));
		file.deleteOnExit();
	}

}
