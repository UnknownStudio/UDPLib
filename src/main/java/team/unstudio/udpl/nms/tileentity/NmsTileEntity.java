package team.unstudio.udpl.nms.tileentity;

import org.bukkit.block.BlockState;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsTileEntity {

	BlockState getBukkitBlockState();
	
	NBTTagCompound save();

	void load(NBTTagCompound nbt);
	
	static NmsTileEntity createNmsTileEntity(BlockState blockState){
		return NmsHelper.createNmsTileEntity(blockState);
	}
}
