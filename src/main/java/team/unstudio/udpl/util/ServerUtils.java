package team.unstudio.udpl.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import team.unstudio.udpl.core.UDPLib;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

/**
 * Server util to quickly
 */
public interface ServerUtils {

	/**
	 * get online players' name
	 */
	static String[] getOnlinePlayerNames(){
		return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).toArray(String[]::new);
	}

	/**
	 * get all online players' name with a filter
	 */
	static String[] getOnlinePlayerNamesWithFilter(Predicate<String> filter){
		return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(filter).toArray(String[]::new);
	}

	/**
	 * cached minecraft version
	 */
	AtomicReference<String> MINECRAFT_VERSION = new AtomicReference<>();

	/**
	 * get minecraft version like "1.11.2"
	 */
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
