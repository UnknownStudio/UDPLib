package team.unstudio.udpl.core.nms.v1_11_R1.entity;

import java.util.Set;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.nms.NmsHelper;

public class NmsEntity<E extends Entity, N extends net.minecraft.server.v1_11_R1.Entity> implements team.unstudio.udpl.nms.entity.NmsEntity{
	
	private final E entity;
	private final N nmsEntity;
	
	@SuppressWarnings("unchecked")
	public NmsEntity(E entity) {
		this.entity = entity;
		nmsEntity = (N) ((CraftEntity) entity).getHandle();
	}

	@Override
	public E getBukkitEntity() {
		return entity;
	}
	
	protected N getNmsEntity() {
		return nmsEntity;
	}

	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagCompound save(){
		return NmsHelper.getNmsNBT().toCompound(getNmsEntity().e(new NBTTagCompound()));
	}

	@Override
	public void load(team.unstudio.udpl.nms.nbt.NBTTagCompound nbt){
		getNmsEntity().f((NBTTagCompound)NmsHelper.getNmsNBT().toNmsNBT(nbt));
	}

	@Override
	public Set<String> getScoreboardTags() {
		return getNmsEntity().P();
	}

	@Override
	public boolean addScoreboardTag(String tag) {
		return getScoreboardTags().add(tag);
	}

	@Override
	public boolean removeScoreboardTag(String tag) {
		return getScoreboardTags().remove(tag);
	}
}
