package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import team.unstudio.udpl.core.UDPLib;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public enum ActionBar {
	
	;
	
	private static final boolean debug = UDPLib.isDebug();
    private static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();


    public static boolean send(Player player, String text){
        PacketContainer container = protocolManager.createPacket(PacketType.Play.Server.CHAT);
        container.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + text + "\"}"));
        container.getBytes().write(0, (byte) 2);

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
