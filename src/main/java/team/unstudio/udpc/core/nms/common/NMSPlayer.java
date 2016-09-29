package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import team.unstudio.udpc.api.nms.NMSManager;
import team.unstudio.udpc.api.nms.ReflectionUtils;

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
		Class<?> cPacket = ReflectionUtils.getNMSClass("Packet");
		if(packet.getClass().isAssignableFrom(cPacket)) return;
		Method getHandle = player.getClass().getDeclaredMethod("getHandle");
		getHandle.setAccessible(true);
		Class<?> player = ReflectionUtils.getNMSClass("EntityPlayer");
		Field playerConnection = player.getDeclaredField("playerConnection");
		playerConnection.setAccessible(true);
		Class<?> cPlayerConnection = ReflectionUtils.getNMSClass("PlayerConnection");
		Method sendPacket = cPlayerConnection.getDeclaredMethod("sendPacket", cPacket);
		sendPacket.setAccessible(true);
		sendPacket.invoke(playerConnection.get(getHandle.invoke(player)),packet);
	}
	
	@Override
	public void sendActionBar(String message) throws Exception{
	    sendPacket(NMSManager.getNMSPacket().createPacketPlayOutChat("{\"text\": \"" + message + "\"}", (byte)2));
	}
}
