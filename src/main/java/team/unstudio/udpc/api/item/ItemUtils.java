package team.unstudio.udpc.api.item;

import org.bukkit.inventory.ItemStack;
import team.unstudio.udpc.api.nms.ReflectionUtils;

import java.lang.reflect.Method;

public class ItemUtils {

    /**
     * 转换为JSON格式
     * @param itemStack 物品
     * @return 
     */
    public static String toJson(ItemStack itemStack){
    	try{
			Class<?> ccitemstack = ReflectionUtils.getCBClass("inventory.CraftItemStack");
			Class<?> citemstack = ReflectionUtils.getNMSClass("ItemStack");
			Class<?> cmap = ReflectionUtils.getNMSClass("NBTTagCompound");
			Object nbt = cmap.newInstance();
			Method asnmscopy = ccitemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
			asnmscopy.setAccessible(true);
			Method save = citemstack.getDeclaredMethod("save",cmap);
			save.setAccessible(true);
			return save.invoke(asnmscopy.invoke(null, itemStack),nbt).toString();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return "";
    }
}
