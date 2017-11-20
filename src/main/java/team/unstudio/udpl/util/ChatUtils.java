package team.unstudio.udpl.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface ChatUtils {

	static String SPLITTER = translateColorCodes("&l&m----------------------------------------------------------------");
	static char DEFAULT_COLOR_CHAR = '&';
	
	/**
	 * 发送分割线
	 */
	static void sendSplitter(Player player){
		player.sendMessage(SPLITTER);
	}
	
	/**
	 * 发送空行
	 */
	static void sendEmpty(Player player){
		player.sendMessage("");
	}
	
	/**
	 * 发送清屏消息
	 */
	static void sendCleanScreen(Player player) {
		for (int i = 0; i < 20; i++)
			sendEmpty(player);
	}
	
	/**
	 * 将文本中'&'转换为颜色字符
	 */
	static String translateColorCodes(String textToTranslate){
		return ChatColor.translateAlternateColorCodes(DEFAULT_COLOR_CHAR, textToTranslate);
	}
}
