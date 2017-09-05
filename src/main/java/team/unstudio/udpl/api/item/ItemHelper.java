package team.unstudio.udpl.api.item;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.api.nms.ReflectionUtils;

import java.lang.reflect.Method;

public enum ItemHelper {

	;
	
    /**
     * 转换为JSON格式
     * @param itemStack 物品
     * @return 
     */
    public static String toJson(ItemStack itemStack){
    	try{
			Class<?> ccitemstack = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
			Class<?> citemstack = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
			Class<?> cmap = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
			Object nbt = cmap.newInstance();
			Method asnmscopy = ccitemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
			asnmscopy.setAccessible(true);
			Method save = citemstack.getDeclaredMethod("save",cmap);
			save.setAccessible(true);
			return save.invoke(asnmscopy.invoke(null, itemStack),nbt).toString();
    	}catch(Exception e){}
    	return "";
    }
}
