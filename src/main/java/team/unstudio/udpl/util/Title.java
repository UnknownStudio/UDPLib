package team.unstudio.udpl.util;

import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public final class Title {
	
	private static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	
	public static void sendTitle(Player player, String title){
		PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.TITLE);
		//TODO:
	}
}
