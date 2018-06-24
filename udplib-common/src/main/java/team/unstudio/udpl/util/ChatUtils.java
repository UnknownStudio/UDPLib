package team.unstudio.udpl.util;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface ChatUtils {

	String SPLITTER = translateColorCodes("&l&m----------------------------------------------------------------");
	char DEFAULT_COLOR_CHAR = '&';

	/**
     * Send splitter line.<br>
	 * 发送分割线
	 */
	static void sendSplitter(CommandSender sender){
		sender.sendMessage(SPLITTER);
	}

	/**
     * Send blank line.<br>
	 * 发送空行
	 */
	static void sendEmpty(CommandSender sender){
		sender.sendMessage("");
	}

	/**
	 * 发送清屏消息
	 */
	static void sendCleanScreen(CommandSender sender) {
		for (int i = 0; i < 20; i++) sendEmpty(sender);
	}

	/**
	 * 将文本中'&'转换为颜色字符
	 */
	static String translateColorCodes(String textToTranslate){
		return ChatColor.translateAlternateColorCodes(DEFAULT_COLOR_CHAR, textToTranslate);
	}

	/**
	 * 玩家停止接收消息<br>
	 * 该方法将会将发送给玩家的消息拦截并缓存，直到调用{@link ChatUtils#startReceiveChat(Player)}方法。
	 * @see ChatUtils#startReceiveChat(Player)
	 */
	static void stopReceiveChat(Player player) {
        throw new NotImplementedException("");
	}

	/**
	 * 玩家开始接收消息<br>
	 * 该方法将会将已拦截的玩家消息发送，并且该发送是逐步的，使玩家可以阅读未读的消息。
	 * @see ChatUtils#stopReceiveChat(Player)
	 */
	static void startReceiveChat(Player player) {
        throw new NotImplementedException("");
	}
}
