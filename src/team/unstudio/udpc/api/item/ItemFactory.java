package team.unstudio.udpc.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import team.unstudio.udpc.api.nms.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Mcdarc on  0019.
 */
public class ItemFactory {
	
    /**
     * 创建一个新物品
     * @param material 物品的类型
     * @param lore 物品的Lore
     * @param name 物品的名称
     * @return 创建的物品
     */
    public static ItemStack createItemStack(Material material, String name, List<String> lore) {
        ItemStack i = new ItemStack(material);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }
    
    /**
     * 创建一个新物品
     * @param lore 物品的Lore
     * @param id 物品的ID
     * @param name 物品的名称
     * @return 创建的物品
     */
    @Deprecated
    public static ItemStack createItemStack(int id, String name, List<String> lore) {
        ItemStack i = new ItemStack(id);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return  i;
    }

    /**
     * 添加附魔
     * @param itemStack ItemStack 物品
     * @param enchantID 附魔的ID
     * @param level 附魔等级
     * @return ItemStack 附魔好的物品
     */
    public static ItemStack addEnchantment(ItemStack itemStack, int enchantID, int level) {
        itemStack.addUnsafeEnchantment(new EnchantmentWrapper(enchantID), level);
        return itemStack;
    }
    
    /**
     * 移除附魔
     * @param itemStack 物品
     * @param enchantID 附魔ID
     * @return
     */
    public static ItemStack removeEnchantment(ItemStack itemStack, int enchantID){
    	itemStack.removeEnchantment(new EnchantmentWrapper(enchantID));
    	return itemStack;
    }

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
