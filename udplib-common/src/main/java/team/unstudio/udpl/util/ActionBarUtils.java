package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.entity.Player;

/**
 * An action bar helper with ProtocolLib,
 * also called overlay message in client.
 */
public interface ActionBarUtils {
    /**
     * sending an action bar to a player
     *
     * @param player the player to send
     * @param text the message to send
     */
    static Result send(Player player, String text){
        PacketContainer container = ProtocolLibUtils.of(PacketType.Play.Server.CHAT);
        container.getChatComponents().write(0, WrappedChatComponent.fromJson("{\"text\": \"" + text + "\"}"));
        if (container.getBytes().size() > 0) {
            container.getBytes().write(0, (byte) 2);
        } else if (container.getEnumModifier(EnumWrappers.ChatType.class, 2).size() > 0) {
            container.getEnumModifier(EnumWrappers.ChatType.class, 2).write(0, EnumWrappers.ChatType.GAME_INFO);
        }

        return ProtocolLibUtils.send(player, container);
    }
}
