package team.unstudio.udpl.core.nms.v1_11_R1;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftBlockState;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.nms.NMSManager;

public class NMSTileEntity implements team.unstudio.udpl.nms.NMSTileEntity{
	
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
		return NMSManager.getNMSNBT().toMap(((CraftBlockState)blockState).getTileEntity().save(new NBTTagCompound()));
	}

	@Override
	public team.unstudio.udpl.nms.NMSTileEntity setNBT(team.unstudio.udpl.nbt.NBTTagCompound nbt) throws Exception{
		((CraftBlockState)blockState).getTileEntity().a((NBTTagCompound)NMSManager.getNMSNBT().toNBT(nbt));
		return this;
	}
	

}
