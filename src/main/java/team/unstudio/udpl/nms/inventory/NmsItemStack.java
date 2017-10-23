package team.unstudio.udpl.nms.inventory;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsItemStack {
	
	ItemStack getItemStack();
	
	NBTTagCompound getTag();

	void setTag(NBTTagCompound nbt);
	
	boolean hasTag();
	
	void load(NBTTagCompound nbt);
	
	NBTTagCompound save();
}
