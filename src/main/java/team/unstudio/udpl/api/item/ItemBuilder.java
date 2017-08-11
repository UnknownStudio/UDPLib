package team.unstudio.udpl.api.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
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

	public ItemBuilder setLocalizedName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLocalizedName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder addLore(String ...lore){
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lores =  getLore(itemStack);
		Collections.addAll(lores, lore);
		setMeta(lores);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder removeLore(int index){
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lores =  getLore(itemStack);
		lores.remove(index);
		setMeta(lores);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder removeLore(String regex){
		ItemMeta meta = itemStack.getItemMeta();
		List<String> lores =  getLore(itemStack);
		for(String lore : lores) {
			if(lore.matches(regex))
				lores.remove(lore);
		}
		setMeta(lores);
		itemStack.setItemMeta(meta);
		return this;
	}

	private List<String> getLore(ItemStack item){
		ItemMeta meta = item.getItemMeta();
		List<String> lore = null;
		if(meta.getLore()==null)
			lore = new ArrayList<String>();
		else lore = meta.getLore();
		return lore;
	}
	
	private void setMeta(List<String> lore) {
		if(lore.isEmpty())return;
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(lore);
	}

	public ItemBuilder addEnchantment(Enchantment ench,int level){
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemBuilder removeEnchantment(Enchantment ench){
		itemStack.removeEnchantment(ench);
		return this;
	}

	public ItemBuilder addFlag(ItemFlag... flags) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(flags);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemBuilder setUnbreakable(boolean value){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setUnbreakable(value);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemStack build(){
		return itemStack.clone();
	}
}
