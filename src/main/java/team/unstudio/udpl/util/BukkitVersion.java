package team.unstudio.udpl.util;

public enum BukkitVersion{
	
	V1_12_R1,
	V1_11_R1,
	V1_10_R1,
	V1_9_R2,
	V1_9_R1,
	V1_8_R3,
	V1_8_R2,
	V1_8_R1,
	UNKNOWN;
			
	public boolean isAbove(BukkitVersion value){
		return compareTo(value) <= 0;
	}
	
	public boolean isBelow(BukkitVersion value){
		return compareTo(value) >= 0;
	}
	
	public String getPackageName(){
		return 'v'+(name().substring(1));
	}
	
	private static BukkitVersion CURRENT_BUKKIT_VERSION;
	public static BukkitVersion getCurrentBukkitVersion(){
		if(CURRENT_BUKKIT_VERSION == null){
			CURRENT_BUKKIT_VERSION = valueOf(ReflectionUtils.PackageType.getServerVersion().toUpperCase());
			if(CURRENT_BUKKIT_VERSION == null)
				CURRENT_BUKKIT_VERSION = BukkitVersion.UNKNOWN;
		}
		return CURRENT_BUKKIT_VERSION;
	}
}
