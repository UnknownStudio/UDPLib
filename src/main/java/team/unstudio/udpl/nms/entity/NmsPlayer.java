package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Player;

public interface NmsPlayer extends NmsEntity{

	Player getBukkitEntity();
	
	/**
	 * 发送一个包
	 * @param packet
	 */
	void sendPacket(Object packet);
	
	/**
	 * 发送一个ActionBar
	 * @param message
	 */
	void sendActionBar(String message);
	
	/**
	 * 发送一个Title
	 * @param title
	 * @param subtitle
	 */
	void sendTitle(String title, String subtitle);
	
	/**
	 * 重置Title
	 */
	void resetTitle();
}
