package team.unstudio.udpl.nms;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.core.nms.asm.AsmNmsManager;
import team.unstudio.udpl.nms.inventory.NmsItemStack;

public final class NmsHelper {
	
	private NmsHelper(){}
	
	private static AsmNmsManager ASM_NMS_MANAGER;

	public static void loadNmsHelper(){
		ASM_NMS_MANAGER = new AsmNmsManager();
	}
	
	public static NmsNBT getNBT(){
		return ASM_NMS_MANAGER.getNmsNBT();
	}
	
	public static NmsItemStack createItemStack(ItemStack itemStack){
		return ASM_NMS_MANAGER.createNmsItemStack(itemStack);
	}
}
