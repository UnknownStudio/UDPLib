package team.unstudio.udpl.api.nms;

import org.bukkit.block.BlockState;

import team.unstudio.udpl.api.nbt.NBTTagCompound;

public interface NMSTileEntity {

	public BlockState getBlockState();
	
	public NBTTagCompound getNBT() throws Exception;

	public NMSTileEntity setNBT(NBTTagCompound nbt) throws Exception;
}
