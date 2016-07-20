package team.unstudio.udpc.core.nms.v1_10_R1;

import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.entity.Entity;

import team.unstudio.udpc.api.nms.NMSManager;

public class NMSEntity implements team.unstudio.udpc.api.nms.NMSEntity{
	
	private final Entity entity;
	
	public NMSEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public Map<String, Object> getNBT() throws Exception {
		Class<?> NBTTagCompound = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagCompound");
		Object nbt = NBTTagCompound.newInstance();
		Class<?> Entity = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".Entity");
		Method getHandle = entity.getClass().getMethod("getHandle");
		getHandle.setAccessible(true);
		Method save = Entity.getMethod("e", NBTTagCompound);
		save.setAccessible(true);
		save.invoke(getHandle.invoke(entity),nbt);
		return NMSManager.getNMSNBT().toMap(nbt);
	}

	@Override
	public team.unstudio.udpc.api.nms.NMSEntity setNBT(Map<String, Object> map) throws Exception {
		Class<?> NBTTagCompound = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagCompound");
		Object nbt = NMSManager.getNMSNBT().toNBT(map);
		Class<?> Entity = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".Entity");
		Method getHandle = entity.getClass().getMethod("getHandle");
		getHandle.setAccessible(true);
		Method f = Entity.getMethod("f", NBTTagCompound);
		f.setAccessible(true);
		f.invoke(getHandle.invoke(entity),nbt);
		return this;
	}

}
