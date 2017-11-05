package team.unstudio.udpl.nms.tileentity;

import org.bukkit.block.BlockState;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsTileEntity {

	BlockState getBukkitBlockState();
	
	NBTTagCompound getNBT() throws Exception;

	void setNBT(NBTTagCompound nbt) throws Exception;
}
