package team.unstudio.udpl.nms;

import org.bukkit.entity.Entity;

import team.unstudio.udpl.nbt.NBTTagCompound;

public interface NMSEntity {

	public Entity getEntity();
	
	public NBTTagCompound getNBT() throws Exception;

	public NMSEntity setNBT(NBTTagCompound nbt) throws Exception;
}
