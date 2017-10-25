package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Entity;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsEntity {

	Entity getEntity();
	
	NBTTagCompound getNBT() throws Exception;

	NmsEntity setNBT(NBTTagCompound nbt) throws Exception;
}
