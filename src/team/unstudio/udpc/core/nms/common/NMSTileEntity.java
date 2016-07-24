package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.block.BlockState;
import team.unstudio.udpc.api.nms.NMSManager;

public class NMSTileEntity implements team.unstudio.udpc.api.nms.NMSTileEntity{
	
	private final BlockState blockState;
	
	public NMSTileEntity(BlockState blockState) {
		this.blockState = blockState;
	}

	@Override
	public BlockState getBlockState() {
		return blockState;
	}
	
	@Override
	public Map<String, Object> getNBT() throws Exception{
		Class<?> NBTTagCompound = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagCompound");
		Object nbt = NBTTagCompound.newInstance();
		Class<?> TileEntity = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".TileEntity");
		Method getTileEntity = blockState.getClass().getMethod("getTileEntity");
		getTileEntity.setAccessible(true);
		Method save = TileEntity.getMethod("save", NBTTagCompound);
		save.setAccessible(true);
		save.invoke(getTileEntity.invoke(blockState),nbt);
		return NMSManager.getNMSNBT().toMap(nbt);
	}

	@Override
	public team.unstudio.udpc.api.nms.NMSTileEntity setNBT(Map<String, Object> map) throws Exception{
		Class<?> NBTTagCompound = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".NBTTagCompound");
		Object nbt = NMSManager.getNMSNBT().toNBT(map);
		Class<?> TileEntity = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".TileEntity");
		Method getTileEntity = blockState.getClass().getMethod("getTileEntity");
		getTileEntity.setAccessible(true);
		Method a = TileEntity.getMethod("a", NBTTagCompound);
		a.setAccessible(true);
		a.invoke(getTileEntity.invoke(blockState),nbt);
		return this;
	}
}
