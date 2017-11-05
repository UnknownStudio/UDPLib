package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.Entity;

import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsEntity {

	Entity getBukkitEntity();
	
	NBTTagCompound save();

	void load(NBTTagCompound nbt);
	
	static NmsEntity createNmsEntity(Entity entity){
		return NmsHelper.createNmsEntity(entity);
	}
}
