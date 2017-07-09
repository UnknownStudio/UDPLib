package team.unstudio.udpl.core.nms.v1_11_R1;

import java.util.Map;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftBlockState;
import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.api.nms.NMSManager;

public class NMSTileEntity implements team.unstudio.udpl.api.nms.NMSTileEntity{
	
	private final BlockState blockState;
	
	public NMSTileEntity(BlockState blockState) {
		this.blockState = blockState;
	}

	@Override
	public BlockState getBlockState() {
		return blockState;
	}
	
	@Override
	public Map<String, Object> getNBT() throws Exception{
		return NMSManager.getNMSNBT().toMap(((CraftBlockState)blockState).getTileEntity().save(new NBTTagCompound()));
	}

	@Override
	public team.unstudio.udpl.api.nms.NMSTileEntity setNBT(Map<String, Object> map) throws Exception{
		((CraftBlockState)blockState).getTileEntity().a((NBTTagCompound)NMSManager.getNMSNBT().toNBT(map));
		return this;
	}
	

}
