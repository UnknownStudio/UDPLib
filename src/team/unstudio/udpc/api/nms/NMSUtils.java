package team.unstudio.udpc.api.nms;

import org.bukkit.Bukkit;

public class NMSUtils {
	
	public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];

	public static Class<?> getNMSClass(String name) throws Exception{
		return Class.forName("net.minecraft.server." + NMS_VERSION + "." +name);
	}
	
	public static Class<?> getCBClass(String name) throws Exception{
		return Class.forName("org.bukkit.craftbukkit." + NMS_VERSION + "." +name);
	}
}
