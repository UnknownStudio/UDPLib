package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.event.FakeSignUpdateEvent;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

public interface SignUtils {
    ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    Set<Player> OPENED_FAKE_SIGN_PLAYERS = Sets.newConcurrentHashSet();

    @Init
    static void initSignUtils() {
        CacheUtils.registerPlayerCache(OPENED_FAKE_SIGN_PLAYERS);

        ProtocolLibUtils.listenOnPacketReceiving(SignUtils::handlePacket, PacketType.Play.Client.UPDATE_SIGN);
    }

    static void handlePacket(PacketEvent event) {
        Player player = event.getPlayer();

        if (!OPENED_FAKE_SIGN_PLAYERS.contains(player)) return;
        OPENED_FAKE_SIGN_PLAYERS.remove(player);

        PacketContainer container = event.getPacket();
        BlockPosition position = container.getBlockPositionModifier().read(0);

        if (position.getY() != 0) return;
        String[] LINES = container.getStringArrays().read(0);
        
        Bukkit.getScheduler().runTask(UDPLib.getPlugin(), () -> Bukkit.getPluginManager()
                .callEvent(new FakeSignUpdateEvent(player, LINES)));
    }

    /**
     * If you want to receive sign update, to see {@link team.unstudio.udpl.event.FakeSignUpdateEvent}
     */
    @SuppressWarnings("deprecation")
    static Result open(@Nonnull Player player, String[] lines) {
        assert lines.length == 4 : "the length of sign's text array must be 4";

        // build fake block's location
        Location playerLocation = player.getLocation();
        BlockPosition blockPosition = new BlockPosition(playerLocation.getBlockX(), 0, playerLocation.getBlockZ());

        {
            // fake sign post block
            PacketContainer container = manager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
            container.getBlockPositionModifier().write(0, blockPosition);
            container.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));

            try {
                manager.sendServerPacket(player, container);
            } catch (InvocationTargetException e) {
                return Result.failure(e);
            }
        }

        {
            // send fake block data contained lines
            PacketContainer container = manager.createPacket(PacketType.Play.Server.UPDATE_SIGN);
            container.getBlockPositionModifier().write(0, blockPosition);

            if (container.getIntegers().size() > 0) container.getIntegers().write(0, 9);

            if (container.getChatComponentArrays().size() > 0) {
                container.getChatComponentArrays().write(0, ProtocolLibUtils.buildChatComponentArray(lines));
            } else if (container.getNbtModifier().size() > 0) {
                container.getNbtModifier().write(0, ProtocolLibUtils.buildStringsNBTBase(lines));
            } else if (container.getStringArrays().size() > 0) {
                container.getStringArrays().write(0, lines);
            }

            try {
                manager.sendServerPacket(player, container);
            } catch (InvocationTargetException e) {
                return Result.failure(e);
            }
        }

        {
            // open sign editor
            PacketContainer container = manager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
            container.getBlockPositionModifier().write(0, blockPosition);
            try {
                manager.sendServerPacket(player, container);
            } catch (InvocationTargetException e) {
                return Result.failure(e);
            }
        }

        OPENED_FAKE_SIGN_PLAYERS.add(player);
        return Result.success();
    }
}
