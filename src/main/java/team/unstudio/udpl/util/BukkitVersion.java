package team.unstudio.udpl.util;

/**
 * Bukkit version checker
 */
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

	/**
	 * if version equal or newer than the version you gave
	 * {@code V1_12_R1.isAbove(V1_8_R1) == true}
	 *
	 * @param value the version to check
	 * @return true if newer or the same
	 */
	public boolean isAbove(BukkitVersion value){
		return compareTo(value) <= 0;
	}


	/**
	 * if version equal or older than the version you gave.
	 * {@code V1_8_R1.isBelow(V1_12_R1) == true}
	 *
	 * @param value the version to check
	 * @return true if older or the same
	 */
	public boolean isBelow(BukkitVersion value){
		return compareTo(value) >= 0;
	}

	/**
	 * get package name like "v1_12_R1"
	 */
	public String getPackageName(){
		return 'v'+(name().substring(1));
	}

	private static BukkitVersion CURRENT_BUKKIT_VERSION;

	/**
	 * get current bukkit version
	 */
	public static BukkitVersion getCurrentBukkitVersion(){
		if(CURRENT_BUKKIT_VERSION == null){
			CURRENT_BUKKIT_VERSION = valueOf(ReflectionUtils.PackageType.getServerVersion().toUpperCase());
			if(CURRENT_BUKKIT_VERSION == null)
				CURRENT_BUKKIT_VERSION = BukkitVersion.UNKNOWN;
		}
		return CURRENT_BUKKIT_VERSION;
	}
}
