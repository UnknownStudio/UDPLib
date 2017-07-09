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
	
	public ItemBuilder setType(Material type){
		itemStack.setType(type);
		return this;
	}
	
	public ItemBuilder setDurability(short durability){
		itemStack.setDurability(durability);
		return this;
	}
	
	public ItemBuilder setAmount(int amount){
		itemStack.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setDisplayName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder addLore(String ...lore){
		ItemMeta meta = itemStack.getItemMeta();
		ArrayList<String> lores = new ArrayList<>(meta.getLore());
		Collections.addAll(lores, lore);
		meta.setLore(lores);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder removeLore(int index){
		ItemMeta meta = itemStack.getItemMeta();
		ArrayList<String> lores = new ArrayList<>(meta.getLore());
		lores.remove(index);
		meta.setLore(lores);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemBuilder addEnchantment(Enchantment ench,int level){
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}
	
	public ItemBuilder removeEnchantment(Enchantment ench){
		itemStack.removeEnchantment(ench);
		return this;
	}
	
	public ItemStack getItemStack(){
		return itemStack;
	}
}
