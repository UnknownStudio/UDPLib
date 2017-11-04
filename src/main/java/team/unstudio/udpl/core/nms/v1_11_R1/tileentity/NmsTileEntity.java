package team.unstudio.udpl.core.nms.v1_11_R1.tileentity;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftBlockState;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.nms.NmsHelper;

public class NmsTileEntity implements team.unstudio.udpl.nms.tileentity.NmsTileEntity{
	
	private final BlockState blockState;
	
	public NmsTileEntity(BlockState blockState) {
		this.blockState = blockState;
	}

	@Override
	public BlockState getBukkitBlockState() {
		return blockState;
	}
	
	@Override
	public team.unstudio.udpl.nms.nbt.NBTTagCompound getNBT() throws Exception{
		return NmsHelper.getNBT().toCompound(((CraftBlockState)blockState).getTileEntity().save(new NBTTagCompound()));
	}

	@Override
	public void setNBT(team.unstudio.udpl.nms.nbt.NBTTagCompound nbt) throws Exception{
		((CraftBlockState)blockState).getTileEntity().a((NBTTagCompound)NmsHelper.getNBT().toNmsNBT(nbt));
	}
	

}
