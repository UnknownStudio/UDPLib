package team.unstudio.udpc.api.nms;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

public interface NMSItemStack {
	
	public Map<String, Object> getNBT(ItemStack itemStack) throws Exception;

	public ItemStack setNBT(ItemStack itemStack,Map<String, Object> map) throws Exception;
}
