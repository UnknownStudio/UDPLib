package team.unstudio.udpl.core.nms.v1_11_R1.entity;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.nms.NmsHelper;

public class NmsEntity implements team.unstudio.udpl.nms.entity.NmsEntity{
	
	private final Entity entity;
	
	public NmsEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Entity getBukkitEntity() {
		return entity;
	}

	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagCompound save(){
		return NmsHelper.getNmsNBT().toCompound(((CraftEntity)entity).getHandle().e(new NBTTagCompound()));
	}

	@Override
	public void load(team.unstudio.udpl.nms.nbt.NBTTagCompound nbt){
		((CraftEntity)entity).getHandle().f((NBTTagCompound)NmsHelper.getNmsNBT().toNmsNBT(nbt));
	}

}
