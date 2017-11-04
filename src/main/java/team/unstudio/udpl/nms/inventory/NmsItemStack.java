package team.unstudio.udpl.nms.inventory;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public interface NmsItemStack {
	
	/**
	 * 获取{@link org.bukkit.inventory.ItemStack}实例
	 * @return
	 */
	ItemStack getItemStack();
	
	/**
	 * 获取ItemStack的NBT数据
	 * {@link NmsItemStack#setTag(NBTTagCompound)}
	 * @return
	 */
	NBTTagCompound getTag();

	/**
	 * 设置ItemStack的NBT数据
	 * {@link NmsItemStack#getTag()}
	 * @param nbt
	 */
	void setTag(NBTTagCompound nbt);
	
	/**
	 * 是否存在NBT数据
	 * [{@link NmsItemStack#getTag()}
	 * @return
	 */
	boolean hasTag();
	
	/**
	 * 从NBT数据载入ItemStack
	 * {@link NmsItemStack#save()}
	 * @param nbt
	 */
	void load(NBTTagCompound nbt);
	
	/**
	 * 保持ItemStack为NBT数据
	 * {@link NmsItemStack#load(NBTTagCompound)}
	 * @return
	 */
	NBTTagCompound save();
}
