package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Entity;

import team.unstudio.udpl.nbt.NBTTagCompound;

public interface NmsEntity {

	public Entity getEntity();
	
	public NBTTagCompound getNBT() throws Exception;

	public NmsEntity setNBT(NBTTagCompound nbt) throws Exception;
}
