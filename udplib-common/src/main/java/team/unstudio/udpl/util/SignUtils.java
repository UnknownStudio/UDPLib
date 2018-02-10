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
import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.event.FakeSignUpdateEvent;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public interface SignUtils {
	
     String version = ServerUtils.getMinecraftVersion();
     ProtocolManager manager = ProtocolLibrary.getProtocolManager();
     PacketType BLOCK_CHANGE = PacketType.Play.Server.BLOCK_CHANGE;
     PacketType UPDATE_SIGN = PacketType.Play.Server.TILE_ENTITY_DATA;
    
     Set<Player> OPENED_FAKE_SIGN_PLAYERS = Sets.newHashSet();
    
     @Init
     static void initSignUtils(){
         CacheUtils.registerPlayerCache(OPENED_FAKE_SIGN_PLAYERS);
         manager.addPacketListener(new PacketListener() {

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
                 if(position.getY() != 0)
                	 return;
                 String line1 = container.getStrings().read(0);
                 String line2 = container.getStrings().read(1);
                 String line3 = container.getStrings().read(2);
                 String line4 = container.getStrings().read(3);
                 Bukkit.getScheduler().runTask(UDPLib.getPlugin(), ()->Bukkit.getPluginManager()
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
                 return UDPLib.getPlugin();
             }
        });
    }

    /**
     * If you want to receive sign update, to see {@link team.unstudio.udpl.event.FakeSignUpdateEvent}
     */
    @SuppressWarnings("deprecation")
	static Result open(Player player, String[] lines){
        Location loc = player.getLocation();
        BlockPosition blockPosition = new BlockPosition(loc.getBlockX(), 0, loc.getBlockZ());
        
        PacketContainer container = manager.createPacket(BLOCK_CHANGE);
        container.getBlockPositionModifier().write(0, blockPosition);
        container.getBlockData().write(0, WrappedBlockData.createData(Material.SIGN_POST));
        try {
			manager.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
        
		if (version.startsWith("1.10.")) {
			container = manager.createPacket(UPDATE_SIGN);
			container.getBlockPositionModifier().write(0, blockPosition);
			container.getChatComponentArrays().write(0,
					new WrappedChatComponent[] { WrappedChatComponent.fromText(lines[0]),
							WrappedChatComponent.fromText(lines[1]), WrappedChatComponent.fromText(lines[2]),
							WrappedChatComponent.fromText(lines[3]) });
		} else if (version.startsWith("1.8.")) {
			container = manager.createPacket(PacketType.Play.Server.UPDATE_SIGN);
			container.getChatComponentArrays().write(0,
					new WrappedChatComponent[] { WrappedChatComponent.fromText(lines[0]),
							WrappedChatComponent.fromText(lines[1]), WrappedChatComponent.fromText(lines[2]),
							WrappedChatComponent.fromText(lines[3]) });
			container.getBlockPositionModifier().write(0, blockPosition);
		} else {
			container = manager.createPacket(UPDATE_SIGN);
			container.getBlockPositionModifier().write(0, blockPosition);
			container.getIntegers().write(0, Integer.valueOf(9));
			NbtCompound nbt = (NbtCompound) container.getNbtModifier().read(0);
			for (int i = 0; i < 4; i++) {
				nbt.put("Text" + (i + 1), "{\"extra\":[{\"text\":\"" + lines[i] + "\"}],\"text\":\"\"}");
			}
			container.getNbtModifier().write(0, nbt);
		}
			
		try {
			manager.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
		
		container = manager.createPacket(PacketType.Play.Server.OPEN_SIGN_EDITOR);
		container.getBlockPositionModifier().write(0, blockPosition);
		try {
			manager.sendServerPacket(player, container);
		} catch (InvocationTargetException e) {
			return Result.failure(e);
		}
		
		OPENED_FAKE_SIGN_PLAYERS.add(player);
		return Result.success();
    }
}
