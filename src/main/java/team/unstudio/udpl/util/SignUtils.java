package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.event.FakeSignUpdateEvent;

import java.util.Set;

public interface SignUtils {
	
     String version = ServerUtils.getMinecraftVersion();
     ProtocolManager manager = ProtocolLibrary.getProtocolManager();
     PacketType BLOCK_CHANGE = PacketType.Play.Server.BLOCK_CHANGE;
     PacketType UPDATE_SIGN = PacketType.Play.Server.TILE_ENTITY_DATA;
     PacketType OPEN_SIGN_ENTITY = PacketType.Play.Server.OPEN_SIGN_EDITOR;
    
     Set<Player> OPENED_FAKE_SIGN_PLAYERS = Sets.newHashSet();
    
     static void initSignUtils(){
         CacheUtils.registerPlayerCache(OPENED_FAKE_SIGN_PLAYERS);
         manager.addPacketListener(new PacketListener() {
        	 
        	 private final BlockPosition zero = new BlockPosition(0, 0, 0);

             @Override
             public void onPacketSending(PacketEvent arg0) {}

             @Override
             public void onPacketReceiving(PacketEvent arg0) {
                 Player player = arg0.getPlayer();
                 if(!OPENED_FAKE_SIGN_PLAYERS.contains(player))
                     return;
                 OPENED_FAKE_SIGN_PLAYERS.remove(player);
                 PacketContainer container = arg0.getPacket();
                 BlockPosition position = container.getBlockPositionModifier().read(0);
                 if(!position.equals(zero))
                	 return;
                 String line1 = container.getStrings().read(0);
                 String line2 = container.getStrings().read(1);
                 String line3 = container.getStrings().read(2);
                 String line4 = container.getStrings().read(3);
                 Bukkit.getScheduler().runTask(UDPLib.getInstance(), ()->Bukkit.getPluginManager()
                         .callEvent(new FakeSignUpdateEvent(player, new String[] { line1, line2, line3, line4 })));
             }

             @Override
             public ListeningWhitelist getSendingWhitelist() {
                 return ListeningWhitelist.EMPTY_WHITELIST;
             }

             @Override
             public ListeningWhitelist getReceivingWhitelist() {
                 return ListeningWhitelist.newBuilder().lowest().types(PacketType.Play.Client.UPDATE_SIGN).build();
             }

             @Override
             public Plugin getPlugin() {
                 return UDPLib.getInstance();
             }
        });
    }

    /**
     * If you want to receive sign update, to see {@link team.unstudio.udpl.event.FakeSignUpdateEvent}
     */
    static Result open(Player player, String[] lines){
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
                OPENED_FAKE_SIGN_PLAYERS.add(player);
                return Result.success();
            } catch (Exception var11) {
            	return Result.failure(var11);
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
                OPENED_FAKE_SIGN_PLAYERS.add(player);
                return Result.success();
            } catch (Exception var12) {
            	return Result.failure(var12);
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
                OPENED_FAKE_SIGN_PLAYERS.add(player);
                return Result.success();
            } catch (Exception var12) {
            	return Result.failure(var12);
            }
        }
    }
}
