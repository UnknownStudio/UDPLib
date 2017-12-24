package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Player;

import java.util.Locale;

public interface NmsPlayer extends NmsEntity{

	Player getBukkitEntity();
	
	Locale getLocale();
	
	/**
	 * 发送一个包
	 */
	void sendPacket(Object packet);
	
	/**
	 * 发送一个ActionBar
	 */
	void sendActionBar(String message);
	
	/**
	 * 发送一个Title
	 */
	void sendTitle(String title, String subtitle);
	
	/**
	 * 重置Title
	 */
	void resetTitle();
}
