package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import team.unstudio.udpc.api.nms.NMSManager;

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
	public void sendPacket(Object packet) throws Exception{
		Class<?> cPacket = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".Packet");
		if(packet.getClass().isAssignableFrom(cPacket)) return;
		Method getHandle = player.getClass().getMethod("getHandle");
		getHandle.setAccessible(true);
		Class<?> player = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".EntityPlayer");
		Field playerConnection = player.getField("playerConnection");
		playerConnection.setAccessible(true);
		Class<?> cPlayerConnection = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".PlayerConnection");
		Method sendPacket = cPlayerConnection.getMethod("sendPacket", cPacket);
		sendPacket.setAccessible(true);
		sendPacket.invoke(playerConnection.get(getHandle.invoke(player)),packet);
	}
}
