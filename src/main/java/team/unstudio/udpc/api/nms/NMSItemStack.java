package team.unstudio.udpc.api.nms;

import java.util.Map;

import org.bukkit.inventory.ItemStack;

public interface NMSItemStack {
	
	public ItemStack getItemStack();
	
	public Map<String, Object> getNBT() throws Exception;

	public NMSItemStack setNBT(Map<String, Object> map) throws Exception;
}
