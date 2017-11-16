package team.unstudio.udpl.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface ChatUtils {

	static String SPLITTER = ChatColor.translateAlternateColorCodes('&', "&l&m----------------------------------------------------------------");
	static char DEFAULT_COLOR_CHAR = '&';
	
	static void sendSplitter(Player player){
		player.sendMessage(SPLITTER);
	}
	
	static void sendEmpty(Player player){
		player.sendMessage("");
	}
	
	static String translateColorCodes(String textToTranslate){
		return ChatColor.translateAlternateColorCodes(DEFAULT_COLOR_CHAR, textToTranslate);
	}
}
