package team.unstudio.udpc.core.nms;

import org.bukkit.Bukkit;

public class NMSManager {
	
	public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	
}
