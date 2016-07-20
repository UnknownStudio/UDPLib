package team.unstudio.udpc.core.nms.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpc.api.nms.NMSManager;

public class NMSItemStack implements team.unstudio.udpc.api.nms.NMSItemStack{
	
	@Override
	public Map<String, Object> getNBT(ItemStack itemStack) throws Exception {
		Class<?> ccitemstack = Class.forName("org.bukkit.craftbukkit." + NMSManager.NMS_VERSION + ".inventory.CraftItemStack");
		Class<?> citemstack = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".ItemStack");
		Method asnmscopy = ccitemstack.getMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method gettag = citemstack.getMethod("getTag");
		gettag.setAccessible(true);
		return NMSManager.getNMSNBT().toMap(gettag.invoke(asnmscopy.invoke(null, itemStack)));
	}

	@Override
	public ItemStack setNBT(ItemStack itemStack, Map<String, Object> map) throws Exception {
		Class<?> ccitemstack = Class.forName("org.bukkit.craftbukkit." + NMSManager.NMS_VERSION + ".inventory.CraftItemStack");
		Class<?> citemstack = Class.forName("net.minecraft.server." + NMSManager.NMS_VERSION + ".ItemStack");
		Method asnmscopy = ccitemstack.getMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method settag = citemstack.getMethod("setTag");
		settag.setAccessible(true);
		Method asbukkitcopy = ccitemstack.getMethod("asBukkitCopy", citemstack);
		asbukkitcopy.setAccessible(true);
		Object nitem = asnmscopy.invoke(null, itemStack);
		settag.invoke(nitem,NMSManager.getNMSNBT().toNBT(map));
		return (ItemStack) asbukkitcopy.invoke(null, nitem);
	}

}
