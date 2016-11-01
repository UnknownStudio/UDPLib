package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.entity.Player;

import team.unstudio.udpc.api.nms.NMSManager;
import team.unstudio.udpc.api.nms.ReflectionUtils;
import team.unstudio.udpc.api.nms.ReflectionUtils.PackageType;

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
		Class<?> cPacket = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet");
		if(!cPacket.isAssignableFrom(packet.getClass())) return;
		Method getHandle = player.getClass().getDeclaredMethod("getHandle");
		getHandle.setAccessible(true);
		Class<?> player = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EntityPlayer");
		Field playerConnection = player.getDeclaredField("playerConnection");
		playerConnection.setAccessible(true);
		Class<?> cPlayerConnection = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("PlayerConnection");
		Method sendPacket = cPlayerConnection.getDeclaredMethod("sendPacket", cPacket);
		sendPacket.setAccessible(true);
		sendPacket.invoke(playerConnection.get(getHandle.invoke(player)),packet);
	}
	
	@Override
	public void sendActionBar(String message) throws Exception{
	    sendPacket(NMSManager.getNMSPacket().createPacketPlayOutChat("{\"text\": \"" + message + "\"}", (byte)2));
	}
	
	public void sendTitle(String title, String subtitle) throws Exception{
		Class<?> enumTitleAction = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");
		Class<?> iChatBaseComponent = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent");
		Method fromString = ReflectionUtils.getMethod(PackageType.CRAFTBUKKIT_UTIL.getClass("CraftChatMessage"),"fromString",String.class);
		Constructor<?> PacketPlayOutTitle = ReflectionUtils.getConstructor("PacketPlayOutTitle", PackageType.MINECRAFT_SERVER, enumTitleAction, iChatBaseComponent);
		if (title != null) {
			Object enumTitle = null;
			for(Object obj:enumTitleAction.getEnumConstants()) if(obj.toString().equals("TITLE")) enumTitle = obj;
			Object packetTitle = PacketPlayOutTitle.newInstance(enumTitle,((Object[])fromString.invoke(null, title))[0]);
			sendPacket(packetTitle);
		}

		if (subtitle != null) {
			Object enumTitle = null;
			for(Object obj:enumTitleAction.getEnumConstants()) if(obj.toString().equals("SUBTITLE")) enumTitle = obj;
			Object packetSubtitle = PacketPlayOutTitle.newInstance(enumTitle,((Object[])fromString.invoke(null, subtitle))[0]);
			sendPacket(packetSubtitle);
		}
	}

	public void resetTitle() throws Exception {
		Class<?> enumTitleAction = PackageType.MINECRAFT_SERVER.getClass("PacketPlayOutTitle$EnumTitleAction");
		Class<?> iChatBaseComponent = PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent");
		Constructor<?> PacketPlayOutTitle = ReflectionUtils.getConstructor("PacketPlayOutTitle", PackageType.MINECRAFT_SERVER, enumTitleAction, iChatBaseComponent);
		Object enumTitle = null;
		for(Object obj:enumTitleAction.getEnumConstants()) if(obj.toString().equals("RESET")) enumTitle = obj;
		Object packetReset = PacketPlayOutTitle.newInstance(enumTitle,null);
		sendPacket(packetReset);
	}
}
