package team.unstudio.udpc.core.nms;

import org.bukkit.Bukkit;

import team.unstudio.udpc.api.nms.NMSNBT;

public class NMSManager {
	
	public static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
	
	public static NMSNBT getNMSNBTCommon(){
		return new team.unstudio.udpc.core.nms.common.NMSNBT();
	}
	
	public static NMSNBT getNMSNBT(){
		switch (NMS_VERSION) {
		case "v1_10_R1":
			return new team.unstudio.udpc.core.nms.v1_10_R1.NMSNBT();
		default:
			return getNMSNBTCommon();
		}
	}
}
