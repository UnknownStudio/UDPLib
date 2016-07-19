package team.unstudio.udpc.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * Created by Mcdarc on  0019.
 */
public class Item {
    /**
     * 创建一个新物品
     * @param lore 物品的Lore
     * @param material 物品的类型
     * @param name 物品的名称
     * @return 创建的物品
     */
    public static ItemStack createItem_material(Material material, List<String> lore, String name) {
        ItemStack i = new ItemStack(material);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return  i;
    }
    /**
     * 创建一个新物品
     * @param lore 物品的Lore
     * @param id 物品的ID
     * @param name 物品的名称
     * @return 创建的物品
     */
    @Deprecated
    public static ItemStack createItem_code(int id, List<String> lore, String name) {
        ItemStack i = new ItemStack(id);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        i.setItemMeta(im);
        return  i;
    }

    /**
     *
     * @param i ItemStack 可以用CreateItem创建
     * @param enchentID 附魔的ID
     * @param Level 附魔等级
     * @return ItemStack 附魔好的物品
     */
    public static ItemStack addEnchantment(ItemStack i, int enchentID, int Level) {
        Enchantment enchantment = new EnchantmentWrapper(enchentID);
        i.addEnchantment(enchantment, Level);
        return i;
    }
}
