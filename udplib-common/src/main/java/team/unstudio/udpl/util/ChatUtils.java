package team.unstudio.udpl.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public final class ChatUtils {

	public static final String SPLITTER = translateColorCodes("&l&m----------------------------------------------------------------");
	public static final char DEFAULT_COLOR_CHAR = '&';
	
	/**
	 * 发送分割线
	 */
	public static void sendSplitter(Player player){
		player.sendMessage(SPLITTER);
	}
	
	/**
	 * 发送空行
	 */
	public static void sendEmpty(Player player){
		player.sendMessage("");
	}
	
	/**
	 * 发送清屏消息
	 */
	public static void sendCleanScreen(Player player) {
		for (int i = 0; i < 20; i++)
			sendEmpty(player);
	}
	
	/**
	 * 将文本中'&'转换为颜色字符
	 */
	public static String translateColorCodes(String textToTranslate){
		return ChatColor.translateAlternateColorCodes(DEFAULT_COLOR_CHAR, textToTranslate);
	}
	
	/**
	 * 玩家停止接收消息<br>
	 * 该方法将会将发送给玩家的消息拦截并缓存，直到调用{@link ChatUtils#startReceiveChat(Player)}方法。
	 * @see ChatUtils#startReceiveChat(Player)
	 */
	public static void stopReceiveChat(Player player) {
		// TODO:
	}
	
	/**
	 * 玩家开始接收消息<br>
	 * 该方法将会将已拦截的玩家消息发送，并且该发送是逐步的，使玩家可以阅读未读的消息。
	 * @see ChatUtils#stopReceiveChat(Player)
	 */
	public static void startReceiveChat(Player player) {
		// TODO:
	}
	
	private ChatUtils() {}
}
