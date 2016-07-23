package team.unstudio.udpc.core.nms.v1_10_R1;

import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_10_R1.Packet;

public class NMSPlayer extends NMSEntity implements team.unstudio.udpc.api.nms.NMSPlayer{
	
	private final Player player;

	public NMSPlayer(Player player) {
		super(player);
		this.player = player;
	}

	@Override
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void sendPacket(Object packet){
		if(!(packet instanceof Packet))return;
		((CraftPlayer)player).getHandle().playerConnection.sendPacket((Packet<?>) packet);
	}
}
