package team.unstudio.udpl.nms.tileentity;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;
import team.unstudio.udpl.nms.nbt.NBTTagList;

public interface NmsMobSpawner extends NmsTileEntity{
	
	CreatureSpawner getBukkitBlockState();
	
	NBTTagList getSpawnEntities();
	
	void addSpawnEntities(NBTTagCompound nbt, int weight);
	
	default void addSpawnEntities(Entity entity, int weight){
		NBTTagCompound entityNbt = NmsHelper.createNmsEntity(entity).save();
		entityNbt.remove("Pos");
		entityNbt.remove("Motion");
		entityNbt.remove("Rotation");
		addSpawnEntities(entityNbt, weight);
	}
	
	NBTTagCompound getSpawnEntity();
	
	void setSpawnEntity(NBTTagCompound nbt);
	
	default void setDisplayEntity(Entity entity){
		NBTTagCompound entityNbt = NmsHelper.createNmsEntity(entity).save();
		entityNbt.remove("Pos");
		entityNbt.remove("Motion");
		entityNbt.remove("Rotation");
		setSpawnEntity(entityNbt);
	}
	
	short getSpawnCount();
	
	void setSpawnCount(short count);
	
	short getSpawnRange();
	
	void setSpawnRange(short range);
	
	short getDelay();
	
	void setDelay(short delay);
	
	short getMinSpawnDelay();
	
	void setMinSpawnDelay(short minDelay);
	
	short getMaxSpawnDelay();
	
	void setMaxSpawnDelay(short maxDelay);
	
	short getMaxNearbyEntities();
	
	void setMaxNearbyEntities(short value);
	
	short getRequiredPlayerRange();
	
	void setRequiredPlayerRange(short range);
}
