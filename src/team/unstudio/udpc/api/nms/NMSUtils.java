package team.unstudio.udpc.api.nms;

public class NMSUtils {

	public static Class<?> getNMSClass(String name) throws Exception{
		return Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + "." +name);
	}
	
	public static Class<?> getCBClass(String name) throws Exception{
		return Class.forName("org.bukkit.craftbukkit." + NMSManager.NMS_VERSION + "." +name);
	}
}
