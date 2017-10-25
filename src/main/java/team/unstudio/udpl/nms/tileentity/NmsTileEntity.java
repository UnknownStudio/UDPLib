package team.unstudio.udpl.nms.tileentity;

import org.bukkit.block.BlockState;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsTileEntity {

	BlockState getBlockState();
	
	NBTTagCompound getNBT() throws Exception;

	NmsTileEntity setNBT(NBTTagCompound nbt) throws Exception;
}
