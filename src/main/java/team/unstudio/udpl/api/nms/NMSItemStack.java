package team.unstudio.udpl.api.nms;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.api.nbt.NBTTagCompound;

public interface NMSItemStack {
	
	public NBTTagCompound getTag(ItemStack itemStack) throws Exception;

	public ItemStack setTag(ItemStack itemStack,NBTTagCompound nbt) throws Exception;
	
	public boolean hasTag(ItemStack itemStack) throws Exception;
}
