package team.unstudio.udpl.nms;

import org.bukkit.block.BlockState;

import team.unstudio.udpl.nbt.NBTTagCompound;

public interface NMSTileEntity {

	public BlockState getBlockState();
	
	public NBTTagCompound getNBT() throws Exception;

	public NMSTileEntity setNBT(NBTTagCompound nbt) throws Exception;
}
