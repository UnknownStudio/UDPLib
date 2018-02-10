package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

/**
 * An action bar helper with ProtocolLib,
 * also called overlay message in client.
 */
public interface ActionBar {
	
	ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    /**
     * sending an action bar to a player
     *
     * @param player the player to send
     * @param text the message to send
     */
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