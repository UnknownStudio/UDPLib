package team.unstudio.udpl.bungeecord;

import org.bukkit.entity.Player;

/**
 * the forward message consumer
 */
@FunctionalInterface
public interface ForwardConsumer {
    /**
     * receiving data
     */
    void accept(String channel, Player player, byte[] data);
}
