package team.unstudio.udpl.api.item;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemWrapper {

	private final ItemStack itemStack;

	public ItemWrapper(){
		this(new ItemStack(Material.AIR));
	}
	
	public ItemWrapper(Material type){
		this(new ItemStack(type));
	}
	
	public ItemWrapper(Material type,int amount){
		this(new ItemStack(type, amount));
	}
	
	public ItemWrapper(Material type,int amount,short damage){
		this(new ItemStack(type, amount, damage));
	}

	public ItemWrapper(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemWrapper setType(Material type){
		itemStack.setType(type);
		return this;
	}

	public ItemWrapper setDurability(short durability){
		itemStack.setDurability(durability);
		return this;
	}

	public ItemWrapper setAmount(int amount){
		itemStack.setAmount(amount);
		return this;
	}

	public ItemWrapper setDisplayName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemWrapper setLocalizedName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLocalizedName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemWrapper addLore(String ...lore){
		List<String> lores =  getLore(itemStack);
		Collections.addAll(lores, lore);
		setMeta(lores);
		return this;
	}

	public ItemWrapper removeLore(int index){
		List<String> lores =  getLore(itemStack);
		lores.remove(index);
		setMeta(lores);
		return this;
	}

	public ItemWrapper removeLore(String regex){
		List<String> lores =  getLore(itemStack);
		for(String s : lores) {
			if(s.matches(regex)) 
				lores.remove(s);
		}
		setMeta(lores);
		return this;
	}

	private List<String> getLore(ItemStack item){
		ItemMeta meta = item.getItemMeta();
		CopyOnWriteArrayList<String> lore = new CopyOnWriteArrayList<String>();
		if(meta.getLore()!=null&&!meta.getLore().isEmpty())
			lore.addAll(meta.getLore());
		return lore;
	}

	private void setMeta(List<String> lore) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
	}

	public ItemWrapper addEnchantment(Enchantment ench,int level){
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemWrapper removeEnchantment(Enchantment ench){
		itemStack.removeEnchantment(ench);
		return this;
	}

	public ItemWrapper addFlag(ItemFlag... flags) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(flags);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemWrapper setUnbreakable(boolean value){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setUnbreakable(value);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemStack get(){
		return itemStack.clone();
	}
}
