package team.unstudio.udpl.core.nms.v1_11_R1;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftBlockState;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.nms.NmsHelper;

public class NmsTileEntity implements team.unstudio.udpl.nms.NmsTileEntity{
	
	private final BlockState blockState;
	
	public NmsTileEntity(BlockState blockState) {
		this.blockState = blockState;
	}

	@Override
	public BlockState getBlockState() {
		return blockState;
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagCompound getNBT() throws Exception{
		return NmsHelper.getNBT().toCompound(((CraftBlockState)blockState).getTileEntity().save(new NBTTagCompound()));
	}

	@Override
	public team.unstudio.udpl.nms.NmsTileEntity setNBT(team.unstudio.udpl.nbt.NBTTagCompound nbt) throws Exception{
		((CraftBlockState)blockState).getTileEntity().a((NBTTagCompound)NmsHelper.getNBT().toNBT(nbt));
		return this;
	}
	

}
