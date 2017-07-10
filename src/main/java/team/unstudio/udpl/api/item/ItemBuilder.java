package team.unstudio.udpl.api.item;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private final ItemStack itemStack;
	
	public ItemBuilder(){
		this(new ItemStack(Material.AIR));
	}
	
	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

    /**
     * 设置物品种类
     * @param type 物品种类
     * @return 修改后的Builder
     */
	public ItemBuilder setType(Material type){
		itemStack.setType(type);
		return this;
	}

    /**
     * 修改物品耐久
     * @param durability 耐久度
     * @return 修改后的Builder
     */
	public ItemBuilder setDurability(short durability){
		itemStack.setDurability(durability);
		return this;
	}

    /**
     * 设置物品数量
     * @param amount 数量
     * @return 修改后的Builder
     */
	public ItemBuilder setAmount(int amount){
		itemStack.setAmount(amount);
		return this;
	}
    /**
     * 设置物品名称
     * @param name 名称
     * @return 修改后的Builder
     */
	public ItemBuilder setDisplayName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

    /**
     * 添加LORE
     * @param lore LORE 可以有多行 不定参数
     * @return 修改后的Builder
     */

	
	public ItemBuilder addLore(String ...lore){
		ItemMeta meta = itemStack.getItemMeta();
		ArrayList<String> lores = new ArrayList<>(meta.getLore());
		Collections.addAll(lores, lore);
		meta.setLore(lores);
		itemStack.setItemMeta(meta);
		return this;
	}

    /**
     * 移除LORE
     * @param index LORE所在行数，从0开始
     * @return 修改后的Builder
     */
	public ItemBuilder removeLore(int index){
		ItemMeta meta = itemStack.getItemMeta();
		ArrayList<String> lores = new ArrayList<>(meta.getLore());
		lores.remove(index);
		meta.setLore(lores);
		itemStack.setItemMeta(meta);
		return this;
	}

    /**
     * 添加物品附魔
     * @param ench 附魔
     * @param level 附魔等级
     * @return 修改后的Builder
     */
	public ItemBuilder addEnchantment(Enchantment ench,int level){
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

    /**
     * 移除物品附魔
     * @param ench 附魔
     * @return 修改后的Builder
     */

	public ItemBuilder removeEnchantment(Enchantment ench){
		itemStack.removeEnchantment(ench);
		return this;
	}

    /**
     * 从本类实例中获取物品
     * @return 物品
     */
	public ItemStack getItemStack(){
		return itemStack;
	}
}
