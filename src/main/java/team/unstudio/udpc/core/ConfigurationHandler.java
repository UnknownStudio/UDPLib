package team.unstudio.udpc.core;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigurationHandler {
	
	public static void reload(){
		UDPCore.INSTANCE.reloadConfig();
		FileConfiguration config = UDPCore.INSTANCE.getConfig();
	}
}
