package team.unstudio.udpl.core.nms.v1_11_R1;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftBlockState;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.nms.NmsManager;

public class NMSTileEntity implements team.unstudio.udpl.nms.NmsTileEntity{
	
	private final BlockState blockState;
	
	public NMSTileEntity(BlockState blockState) {
		this.blockState = blockState;
	}

	@Override
	public BlockState getBlockState() {
		return blockState;
	}
	
	@Override
	public team.unstudio.udpl.nbt.NBTTagCompound getNBT() throws Exception{
		return NmsManager.getNBT().toMap(((CraftBlockState)blockState).getTileEntity().save(new NBTTagCompound()));
	}

	@Override
	public team.unstudio.udpl.nms.NmsTileEntity setNBT(team.unstudio.udpl.nbt.NBTTagCompound nbt) throws Exception{
		((CraftBlockState)blockState).getTileEntity().a((NBTTagCompound)NmsManager.getNBT().toNBT(nbt));
		return this;
	}
	

}
