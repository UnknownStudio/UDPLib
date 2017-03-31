package team.unstudio.udpl.core.nms.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.api.nms.NMSManager;
import team.unstudio.udpl.api.nms.ReflectionUtils;

public class NMSItemStack implements team.unstudio.udpl.api.nms.NMSItemStack{
	
	@Override
	public Map<String, Object> getTag(ItemStack itemStack) throws Exception {
		Class<?> ccitemstack = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
		Class<?> citemstack = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
		Method asnmscopy = ccitemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method gettag = citemstack.getDeclaredMethod("getTag");
		gettag.setAccessible(true);
		return NMSManager.getNMSNBT().toMap(gettag.invoke(asnmscopy.invoke(null, itemStack)));
	}

	@Override
	public ItemStack setTag(ItemStack itemStack, Map<String, Object> map) throws Exception {
		Class<?> ccitemstack = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
		Class<?> citemstack = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
		Method asnmscopy = ccitemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method settag = citemstack.getDeclaredMethod("setTag");
		settag.setAccessible(true);
		Method asbukkitcopy = ccitemstack.getDeclaredMethod("asBukkitCopy", citemstack);
		asbukkitcopy.setAccessible(true);
		Object nitem = asnmscopy.invoke(null, itemStack);
		settag.invoke(nitem,NMSManager.getNMSNBT().toNBT(map));
		return (ItemStack) asbukkitcopy.invoke(null, nitem);
	}

	@Override
	public boolean hasTag(ItemStack itemStack) throws Exception {
		Class<?> ccitemstack = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
		Class<?> citemstack = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
		Method asnmscopy = ccitemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
		asnmscopy.setAccessible(true);
		Method hastag = citemstack.getDeclaredMethod("hasTag");
		hastag.setAccessible(true);
		return (boolean) hastag.invoke(asnmscopy.invoke(null, itemStack));
	}

}
