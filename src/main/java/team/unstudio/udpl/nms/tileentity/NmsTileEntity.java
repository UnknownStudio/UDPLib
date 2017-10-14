package team.unstudio.udpl.nms.tileentity;

import org.bukkit.block.BlockState;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsTileEntity {

	public BlockState getBlockState();
	
	public NBTTagCompound getNBT() throws Exception;

	public NmsTileEntity setNBT(NBTTagCompound nbt) throws Exception;
}
