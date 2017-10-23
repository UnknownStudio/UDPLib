package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;

import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public interface ActionBar {
	
	ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    static Result send(Player player, String text){
        PacketContainer container = PROTOCOL_MANAGER.createPacket(PacketType.Play.Server.CHAT);
        container.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + text + "\"}"));
        container.getBytes().write(0, (byte) 2);

        try {
            PROTOCOL_MANAGER.sendServerPacket(player, container);
            return Result.success();
        } catch (InvocationTargetException e) {
        	return Result.failure(e);
        }
    }
}
