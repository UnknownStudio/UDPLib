package team.unstudio.udpl.nms;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nms.entity.NmsEntity;
import team.unstudio.udpl.nms.inventory.NmsItemStack;
import team.unstudio.udpl.nms.nbt.NmsNBT;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;

public interface NmsManager {
	
	NmsNBT getNmsNBT();
	
	NmsItemStack createNmsItemStack(ItemStack itemStack);
	
	NmsEntity createNmsEntity(Entity entity);
	
	NmsTileEntity createNmsTileEntity(BlockState state);
}
