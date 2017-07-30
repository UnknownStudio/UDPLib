package team.unstudio.udpl.api.nms;

import org.bukkit.entity.Entity;

import team.unstudio.udpl.api.nbt.NBTTagCompound;

public interface NMSEntity {

	public Entity getEntity();
	
	public NBTTagCompound getNBT() throws Exception;

	public NMSEntity setNBT(NBTTagCompound nbt) throws Exception;
}
