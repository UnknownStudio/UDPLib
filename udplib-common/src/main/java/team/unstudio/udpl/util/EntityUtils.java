package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.base.Strings;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public interface EntityUtils {
    AtomicInteger nextEntityID = new AtomicInteger(Integer.MAX_VALUE);

	@Deprecated
	static Result sendFakeItemEntity(@Nonnull Player player, @Nonnull ItemStack itemStack, @Nonnull Location location, @Nullable String displayName){
		int entityID = nextEntityID.getAndDecrement();
		PacketContainer spawnEntityLiving = ProtocolLibUtils.of(PacketType.Play.Server.SPAWN_ENTITY_LIVING);
		spawnEntityLiving.getIntegers().write(0, entityID); //Entity ID
		spawnEntityLiving.getUUIDs().write(0, UUID.randomUUID()); //Entity UUID
		spawnEntityLiving.getIntegers().write(1, 2); //Entity Type
		spawnEntityLiving.getDoubles().write(0, location.getX())
							  .write(1, location.getY())
							  .write(2, location.getZ());
		spawnEntityLiving.getIntegers().write(2, 0) //Pitch
									   .write(3, 0)	//Yaw
									   //Data
									   .write(4, 1)
									   //Velocity(X,Y,Z)
									   .write(5, 0)
									   .write(6, 0)
									   .write(7, 0);

		PacketContainer entityMetadata = ProtocolLibUtils.of(PacketType.Play.Server.ENTITY_METADATA);
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

		return ProtocolLibUtils.send(player, spawnEntityLiving, entityMetadata);
	}
}
