package team.unstudio.udpl.nms.entity;

import java.util.Set;

import org.bukkit.entity.Entity;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsEntity {

	Entity getBukkitEntity();
	
	NBTTagCompound save();

	void load(NBTTagCompound nbt);
	
	Set<String> getScoreboardTags();

    boolean addScoreboardTag(String tag);

    boolean removeScoreboardTag(String tag);
	
	static NmsEntity createNmsEntity(Entity entity){
		return NmsHelper.createNmsEntity(entity);
	}
}
