package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;

import team.unstudio.udpl.core.UDPLib;

public interface BlockUtils {
	
	boolean debug = UDPLib.isDebug();
	ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	
	static boolean sendBlockBreakAnimation(Player player, Location location, byte state){
		 PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
		 container.getIntegers().write(0, player.getEntityId());
		 container.getBlockPositionModifier().write(0, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
		 container.getBytes().write(0, state);
		 
	        try {
	            protocolManager.sendServerPacket(player, container);
	            return true;
	        } catch (InvocationTargetException e) {
	           if(debug) 
	        	   e.printStackTrace();
	        }
	        return false;
	}
}
