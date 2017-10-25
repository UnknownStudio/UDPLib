package team.unstudio.udpl.util;

import org.bukkit.Bukkit;
import team.unstudio.udpl.core.UDPLib;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

public interface ServerUtils {
	
	static String[] getOnlinePlayerNames(){
		return Bukkit.getOnlinePlayers().stream().map(player->player.getName()).toArray(String[]::new);
	}
	
	static String[] getOnlinePlayerNamesWithFilter(Predicate<String> filter){
		return Bukkit.getOnlinePlayers().stream().map(player->player.getName()).filter(filter).toArray(String[]::new);
	}
	
	AtomicReference<String> MINECRAFT_VERSION = new AtomicReference<>();

	static String getMinecraftVersion() {
		if (MINECRAFT_VERSION.get() == null) {
			try {
				MINECRAFT_VERSION.set((String) ReflectionUtils
						.invokeMethod(ReflectionUtils.getValue(Bukkit.getServer(), true, "console"), "getVersion"));
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | NoSuchFieldException | SecurityException e) {
				if(UDPLib.isDebug())
					e.printStackTrace();
				String bukkitVersion = Bukkit.getBukkitVersion();
				int index = Bukkit.getBukkitVersion().indexOf("-");
				MINECRAFT_VERSION.set(bukkitVersion.substring(0, index == -1 ? bukkitVersion.length() : index));
			}
		}
		return MINECRAFT_VERSION.get();
	}
}
