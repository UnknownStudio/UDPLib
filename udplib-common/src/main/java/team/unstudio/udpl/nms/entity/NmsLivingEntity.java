package team.unstudio.udpl.nms.entity;

import org.bukkit.entity.LivingEntity;

public interface NmsLivingEntity extends NmsEntity{
	
	LivingEntity getBukkitEntity();
}
