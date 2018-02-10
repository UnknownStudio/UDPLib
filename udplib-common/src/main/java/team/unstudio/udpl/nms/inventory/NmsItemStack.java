package team.unstudio.udpl.nms.inventory;

import org.bukkit.inventory.ItemStack;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;

/**
 * {@link NmsHelper#createNmsItemStack(ItemStack)}
 */
public interface NmsItemStack {
	
	/**
	 * 获取{@link ItemStack}实例
	 */
	ItemStack getBukkitItemStack();
	
	/**
	 * 获取ItemStack的NBT数据
	 * {@link NmsItemStack#setTag(NBTTagCompound)}
	 */
	NBTTagCompound getTag();

	/**
	 * 设置ItemStack的NBT数据
	 * {@link NmsItemStack#getTag()}
	 */
	void setTag(NBTTagCompound nbt);
	
	/**
	 * 是否存在NBT数据
	 * [{@link NmsItemStack#getTag()}
	 */
	boolean hasTag();
	
	/**
	 * 从NBT数据载入ItemStack
	 * {@link NmsItemStack#save()}
	 */
	void load(NBTTagCompound nbt);
	
	/**
	 * 保持ItemStack为NBT数据
	 * {@link NmsItemStack#load(NBTTagCompound)}
	 */
	NBTTagCompound save();
	
	/**
	 * 创建一个NmsItemStack对象
	 */
	static NmsItemStack createNmsItemStack(ItemStack itemStack){
		return NmsHelper.createNmsItemStack(itemStack);
	}
}
