package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Player;

public interface NmsPlayer extends NmsEntity{

	Player getBukkitPlayer();
	
	/**
	 * 发送一个包
	 * @param packet
	 * @throws Exception
	 */
	void sendPacket(Object packet) throws Exception;
	
	/**
	 * 发送一个ActionBar
	 * @param message
	 * @throws Exception
	 */
	void sendActionBar(String message) throws Exception;
	
	/**
	 * 发送一个Title
	 * @param title
	 * @param subtitle
	 * @throws Exception
	 */
	void sendTitle(String title, String subtitle) throws Exception;
	
	/**
	 * 重置Title
	 * @throws Exception
	 */
	void resetTitle() throws Exception;
}
