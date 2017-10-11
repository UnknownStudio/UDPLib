package team.unstudio.udpl.util;

import team.unstudio.udpl.nms.ReflectionUtils;

public enum BukkitVersion{
	
	V1_12_R1,
	V1_11_R1,
	V1_10_R1,
	V1_9_R2,
	V1_9_R1,
	V1_8_R3,
	V1_8_R2,
	V1_8_R1;

	public static final BukkitVersion CURRENT_BUKKIT_VERSION = valueOf(ReflectionUtils.PackageType.getServerVersion().toUpperCase());
			
	public boolean isAbove(BukkitVersion value){
		return compareTo(value) <= 0;
	}
	
	public boolean isBelow(BukkitVersion value){
		return compareTo(value) >= 0;
	}
}
