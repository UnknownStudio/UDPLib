package team.unstudio.udpc.api.nms;

import org.bukkit.entity.Player;

public interface NMSPlayer extends NMSEntity{

	public Player getPlayer();
	
	public void sendPacket(Object packet) throws Exception;
	
	public void sendActionBar(String message) throws Exception;
	
	public void sendTitle(String title, String subtitle) throws Exception;
	
	public void resetTitle() throws Exception;
}
