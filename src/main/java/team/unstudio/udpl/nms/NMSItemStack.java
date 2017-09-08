package team.unstudio.udpl.nms;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nbt.NBTTagCompound;

public interface NMSItemStack {
	
	public NBTTagCompound getTag(ItemStack itemStack) throws Exception;

	public ItemStack setTag(ItemStack itemStack,NBTTagCompound nbt) throws Exception;
	
	public boolean hasTag(ItemStack itemStack) throws Exception;
}
