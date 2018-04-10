package team.unstudio.udpl.core.nms.v1_11_R1.tileentity;

import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_11_R1.block.CraftBlockState;
import net.minecraft.server.v1_11_R1.TileEntity;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public class NmsTileEntity<B extends BlockState, T extends TileEntity> implements team.unstudio.udpl.nms.tileentity.NmsTileEntity{
	
	private final T tileEntity;
	
	@SuppressWarnings("unchecked")
	public NmsTileEntity(BlockState blockState) {
		this.tileEntity = (T) ((CraftBlockState)blockState).getTileEntity();
	}
	
	protected T getTileEntity(){
		return tileEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public B getBukkitBlockState() {
		return (B) CraftBlockState.getBlockState(tileEntity.getWorld(), tileEntity.getPosition().getX(), tileEntity.getPosition().getY(), tileEntity.getPosition().getZ());
	}
	
	@Override
	public NBTTagCompound save(){
		return NmsHelper.getNmsNBT().toCompound(tileEntity.save(new net.minecraft.server.v1_11_R1.NBTTagCompound()));
	}

	@Override
	public void load(NBTTagCompound nbt){
		tileEntity.a((net.minecraft.server.v1_11_R1.NBTTagCompound)NmsHelper.getNmsNBT().toNmsNBT(nbt));
	}
}
