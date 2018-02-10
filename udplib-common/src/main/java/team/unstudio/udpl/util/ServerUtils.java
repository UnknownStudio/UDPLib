package team.unstudio.udpl.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import team.unstudio.udpl.UDPLib;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Server util to quickly
 */
public interface ServerUtils {

	/**
	 * get online players' name
	 */
	static List<String> getOnlinePlayerNames(){
		return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList());
	}

	/**
	 * get all online players' name with a filter
	 */
	static List<String> getOnlinePlayerNamesWithFilter(Predicate<String> filter){
		return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(filter).collect(Collectors.toList());
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
				UDPLib.debug(e);
				String bukkitVersion = Bukkit.getBukkitVersion();
				int index = Bukkit.getBukkitVersion().indexOf("-");
				MINECRAFT_VERSION.set(bukkitVersion.substring(0, index == -1 ? bukkitVersion.length() : index));
			}
		}
		return MINECRAFT_VERSION.get();
	}
}
