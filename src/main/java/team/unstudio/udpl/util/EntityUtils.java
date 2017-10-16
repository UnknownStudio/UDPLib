package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.base.Strings;

public final class EntityUtils {

	private EntityUtils(){}
	
	private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	private static int nextEntityID = Integer.MAX_VALUE;
	
	@Deprecated
	public static Result sendFakeItemEntity(@Nonnull Player player,@Nonnull ItemStack itemStack,@Nonnull Location location,@Nullable String displayName){
		Validate.notNull(player);
		Validate.notNull(itemStack);
		Validate.notNull(location);
		
		int entityID = nextEntityID--;
		PacketContainer spawnEntityLiving = protocolManager.createPacket(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		spawnEntityLiving.getIntegers().write(0, entityID); //Entity ID
		spawnEntityLiving.getUUIDs().write(0, UUID.randomUUID()); //Entity UUID
		spawnEntityLiving.getBytes().write(0, (byte) 2); //Entity Type
		spawnEntityLiving.getDoubles().write(0, location.getX())
							  .write(1, location.getY())
							  .write(2, location.getZ());
		spawnEntityLiving.getBytes().write(1, (byte) 0) 
							.write(2, (byte) 0);
		spawnEntityLiving.getIntegers().write(0, 1); //Data
		spawnEntityLiving.getShorts().write(0, (short) 0)
							 .write(1, (short) 0)
							 .write(2, (short) 0);
		
		PacketContainer entityMetadata = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
		entityMetadata.getIntegers().write(0, entityID);
		WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
		dataWatcher.setObject(0, (byte) 0);
		dataWatcher.setObject(1, 300);
		dataWatcher.setObject(2, Strings.nullToEmpty(displayName));
		dataWatcher.setObject(3, !Strings.isNullOrEmpty(displayName));
		dataWatcher.setObject(4, false);
		dataWatcher.setObject(5, true);
		dataWatcher.setObject(6, itemStack);
		entityMetadata.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
        try {
            protocolManager.sendServerPacket(player, spawnEntityLiving);
            protocolManager.sendServerPacket(player, entityMetadata);
            return Result.success();
        } catch (InvocationTargetException e) {
        	return Result.failure(e);
        }
	}
}
