package team.unstudio.udpc.api.nms;

import org.bukkit.entity.Player;

public interface NMSPlayer extends NMSEntity{

	public Player getPlayer();
	
	public void sendPacket(Object packet) throws Exception;
}
