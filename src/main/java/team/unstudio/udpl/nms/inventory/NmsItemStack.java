package team.unstudio.udpl.nms.inventory;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nbt.NBTTagCompound;

public interface NmsItemStack {
	
	public ItemStack getItemStack();
	
	public NBTTagCompound getTag();

	public void setTag(NBTTagCompound nbt);
	
	public boolean hasTag();
	
	public void load(NBTTagCompound nbt);
	
	public NBTTagCompound save();
}
