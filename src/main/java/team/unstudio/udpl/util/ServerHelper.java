package team.unstudio.udpl.util;

import java.util.function.Predicate;

import org.bukkit.Bukkit;

public interface ServerHelper {

	public static String[] getOnlinePlayerNames(){
		return Bukkit.getOnlinePlayers().stream().map(player->player.getName()).toArray(String[]::new);
	}
	
	public static String[] getOnlinePlayerNamesWithFilter(Predicate<String> filter){
		return Bukkit.getOnlinePlayers().stream().map(player->player.getName()).filter(filter).toArray(String[]::new);
	}
}
