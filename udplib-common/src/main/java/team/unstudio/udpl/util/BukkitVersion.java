package team.unstudio.udpl.util;

import team.unstudio.udpl.util.reflect.ReflectionUtils;

/**
 * Bukkit version checker
 */
public enum BukkitVersion {

	V1_13_R2("v1_13_R2","1.13.1"),
	V1_13_R1("v1_13_R1","1.13"),
	V1_12_R1("v1_12_R1","1.12.2"),
	V1_11_R1("v1_11_R1","1.11.2"),
	V1_10_R1("v1_10_R1","1.10.2"),
	V1_9_R2("v1_9_R2","1.9.4"),
	V1_9_R1("v1_9_R1","1.9"),
	V1_8_R3("v1_8_R3","1.8.7"),
	V1_8_R2("v1_8_R2","1.8.3"),
	V1_8_R1("v1_8_R1","1.8"),
	UNKNOWN(ReflectionUtils.PackageType.getServerVersion(), ServerUtils.getMinecraftVersion());

	private final String packetName;
	private final String lastMinecraftVersion;

	BukkitVersion(String packetName,String lastMinecraftVersion) {
		this.packetName = packetName;
		this.lastMinecraftVersion = lastMinecraftVersion;
	}

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
     * if version equal the current version.
     * In version 1.12.2, you will get
     * {@code V1_12_R1.isCurrent() == true}
     *
     * @return true if equal the current version
     */
	public boolean isCurrent() {
	   return this == getCurrentBukkitVersion();
    }

	/**
	 * get package name like "v1_12_R1"
	 */
	public String getPackageName(){
		return packetName;
	}

	public String getLastMinecraftVersion(){
		return lastMinecraftVersion;
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
