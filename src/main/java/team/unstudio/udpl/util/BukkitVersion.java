package team.unstudio.udpl.util;

import team.unstudio.udpl.nms.ReflectionUtils;

public enum BukkitVersion{
	
	V1_12_R1(1201),
	V1_11_R1(1101),
	V1_10_R1(1001),
	V1_9_R2(902),
	V1_9_R1(901),
	V1_8_R3(803),
	V1_8_R2(802),
	V1_8_R1(801);

	public static final BukkitVersion CURRENT_BUKKIT_VERSION = valueOf(ReflectionUtils.PackageType.getServerVersion().toUpperCase());
			
	private final int index;
	
	BukkitVersion(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
