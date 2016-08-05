package team.unstudio.udpc.api.nms;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

public interface NMSItemStack {
	
	public Map<String, Object> getTag(ItemStack itemStack) throws Exception;

	public ItemStack setTag(ItemStack itemStack,Map<String, Object> map) throws Exception;
	
	public boolean hasTag(ItemStack itemStack) throws Exception;
}
