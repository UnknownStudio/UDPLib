package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Predicate;

import org.bukkit.Bukkit;

public final class ServerUtils {

	private ServerUtils(){}
	
	public static String[] getOnlinePlayerNames(){
		return Bukkit.getOnlinePlayers().stream().map(player->player.getName()).toArray(String[]::new);
	}
	
	public static String[] getOnlinePlayerNamesWithFilter(Predicate<String> filter){
		return Bukkit.getOnlinePlayers().stream().map(player->player.getName()).filter(filter).toArray(String[]::new);
	}
	
	private static String MINECRAFT_VERSION = null;

	public static String getMinecraftVersion() {
		if (MINECRAFT_VERSION == null) {
			try {
				MINECRAFT_VERSION = (String) ReflectionUtils
						.invokeMethod(ReflectionUtils.getValue(Bukkit.getServer(), true, "console"), "getVersion");
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | NoSuchFieldException | SecurityException e) {
				String bukkitVersion = Bukkit.getBukkitVersion();
				int index = Bukkit.getBukkitVersion().indexOf("-");
				MINECRAFT_VERSION = bukkitVersion.substring(0, index == -1 ? bukkitVersion.length() : index);
			}
		}
		return MINECRAFT_VERSION;
	}
}
