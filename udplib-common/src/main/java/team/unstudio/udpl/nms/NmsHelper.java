package team.unstudio.udpl.nms;

import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.nms.entity.NmsEntity;
import team.unstudio.udpl.nms.inventory.NmsItemStack;
import team.unstudio.udpl.nms.nbt.NmsNBT;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;

public final class NmsHelper {
	
	private NmsHelper(){}
	
	@Init
	private static NmsManager NMS_MANAGER;

	/**
	 * 获取 {@link net.minecraft.server} 与  {@link team.unstudio.udpl.nms.nbt} 的帮助类实例
	 */
	public static NmsNBT getNmsNBT(){
		return NMS_MANAGER.getNmsNBT();
	}
	
	
	/**
	 * 创建一个{@link NmsItemStack}对象
	 */
	public static NmsItemStack createNmsItemStack(ItemStack itemStack){
		return NMS_MANAGER.createNmsItemStack(itemStack);
	}
	
	/**
	 * 创建一个{@link NmsEntity}对象
	 */
	public static NmsEntity createNmsEntity(Entity entity){
		return NMS_MANAGER.createNmsEntity(entity);
	}
	
	/**
	 * 创建一个{@link NmsTileEntity}对象
	 */
	public static NmsTileEntity createNmsTileEntity(BlockState blockState){
		return NMS_MANAGER.createNmsTileEntity(blockState);
	}
}
