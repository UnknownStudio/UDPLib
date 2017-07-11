package team.unstudio.udpl.api.common;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * Created by trychen on 17/7/11.
 */
public class Sign {
    protected static String version = Bukkit.getServer().getBukkitVersion().substring(0, 3);
    private static ProtocolManager manager = ProtocolLibrary.getProtocolManager();
    private static PacketType BLOCK_CHANGE = PacketType.Play.Server.BLOCK_CHANGE;
    private static PacketType UPDATE_SIGN = PacketType.Play.Server.TILE_ENTITY_DATA;
    private static PacketType OPEN_SIGN_ENTITY = PacketType.Play.Server.OPEN_SIGN_EDITOR;

    public static void open(Player player, String[] lines){
        Location loc = player.getLocation();
        int x = loc.getBlockX();
        int y = 0;
        int z = loc.getBlockZ();
        BlockPosition blockPosition = new BlockPosition(x, y, z);

        if (version.startsWith("1.10.")) {
            try {
                PacketContainer e = manager.createPacket(BLOCK_CHANGE);
                e.getBlockPositionModifier().write(0, blockPosition);
                e.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));
                manager.sendServerPacket(player, e);
                e = manager.createPacket(UPDATE_SIGN);
                e.getBlockPositionModifier().write(0, blockPosition);
                e.getChatComponentArrays().write(0, new WrappedChatComponent[]{WrappedChatComponent.fromText(lines[0]), WrappedChatComponent.fromText(lines[1]), WrappedChatComponent.fromText(lines[2]), WrappedChatComponent.fromText(lines[3])});
                manager.sendServerPacket(player, e);
                e = manager.createPacket(OPEN_SIGN_ENTITY);
                e.getBlockPositionModifier().write(0, blockPosition);
                manager.sendServerPacket(player, e);
            } catch (Exception var11) {
                var11.printStackTrace();
            }
        } else if (version.startsWith("1.8.")) {
            try {
                PacketContainer e = manager.createPacket(BLOCK_CHANGE);
                e.getBlockPositionModifier().write(0, blockPosition);
                e.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));
                manager.sendServerPacket(player, e);
                e = manager.createPacket(PacketType.Play.Server.UPDATE_SIGN);
                e.getChatComponentArrays().write(0, new WrappedChatComponent[]{WrappedChatComponent.fromText(lines[0]), WrappedChatComponent.fromText(lines[1]), WrappedChatComponent.fromText(lines[2]), WrappedChatComponent.fromText(lines[3])});
                e.getBlockPositionModifier().write(0, blockPosition);
                manager.sendServerPacket(player, e);
                e = manager.createPacket(OPEN_SIGN_ENTITY);
                e.getBlockPositionModifier().write(0, blockPosition);
                manager.sendServerPacket(player, e);
            } catch (Exception var12) {
                var12.printStackTrace();
            }
        }else {
            try {
                PacketContainer e = manager.createPacket(BLOCK_CHANGE);
                e.getBlockPositionModifier().write(0, blockPosition);
                e.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));
                manager.sendServerPacket(player, e);
                e = manager.createPacket(UPDATE_SIGN);
                e.getBlockPositionModifier().write(0, blockPosition);
                e.getIntegers().write(0, Integer.valueOf(9));
                NbtCompound nbt = (NbtCompound) e.getNbtModifier().read(0);
                for (int i = 0; i < 4; i++) {
                    nbt.put("Text" + (i + 1), "{\"extra\":[{\"text\":\"" + lines[i] + "\"}],\"text\":\"\"}");
                }
                e.getNbtModifier().write(0, nbt);
                manager.sendServerPacket(player, e);
                e = manager.createPacket(OPEN_SIGN_ENTITY);
                e.getBlockPositionModifier().write(0, blockPosition);
                manager.sendServerPacket(player, e);
            } catch (Exception var12) {
                var12.printStackTrace();
            }
        }
    }
}
