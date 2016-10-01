package team.unstudio.udpc.api.item;

import java.util.ArrayList;
import java.util.Collections;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {

	private final ItemStack itemStack;
	
	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
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
}
