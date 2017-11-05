package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Entity;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsEntity {

	Entity getBukkitEntity();
	
	NBTTagCompound getNBT() throws Exception;

	NmsEntity setNBT(NBTTagCompound nbt) throws Exception;
}
