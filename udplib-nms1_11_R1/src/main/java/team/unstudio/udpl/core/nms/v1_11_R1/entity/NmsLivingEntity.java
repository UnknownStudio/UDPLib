package team.unstudio.udpl.core.nms.v1_11_R1.entity;

import org.bukkit.entity.LivingEntity;

import net.minecraft.server.v1_11_R1.EntityLiving;

public class NmsLivingEntity<E extends LivingEntity, N extends EntityLiving> extends NmsEntity<E, N> implements team.unstudio.udpl.nms.entity.NmsLivingEntity{

	public NmsLivingEntity(E entity) {
		super(entity);
	}

}
