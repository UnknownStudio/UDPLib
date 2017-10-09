package team.unstudio.udpl.item;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.common.collect.Lists;

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
	
	public Material getType(){
		return itemStack.getType();
	}

	public ItemWrapper setType(Material type){
		itemStack.setType(type);
		return this;
	}
	
	public short getDurability(){
		return itemStack.getDurability();
	}

	public ItemWrapper setDurability(short durability){
		itemStack.setDurability(durability);
		return this;
	}
	
	public int getAmount(){
		return itemStack.getAmount();
	}

	public ItemWrapper setAmount(int amount){
		itemStack.setAmount(amount);
		return this;
	}
	
	public String getDisplayName(){
		ItemMeta meta = itemStack.getItemMeta();
		return meta.hasDisplayName()?meta.getDisplayName():"";
	}

	public ItemWrapper setDisplayName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public String getLocalizedName(){
		ItemMeta meta = itemStack.getItemMeta();
		return meta.hasLocalizedName()?meta.getLocalizedName():"";
	}

	public ItemWrapper setLocalizedName(String name){
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLocalizedName(name);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public List<String> getLore(){
		ItemMeta meta = itemStack.getItemMeta();
		if(meta.hasLore())
			return meta.getLore();
		else
			return Lists.newArrayList();
	}
	
	public ItemWrapper setLore(String... lore){
		setLore(Arrays.asList(lore));
		return this;
	}
	
	public ItemWrapper setLore(List<String> lore){
		itemStack.getItemMeta().setLore(lore);
		return this;
	}

	public ItemWrapper addLore(String... lore){
		List<String> lores = getLore();
		Collections.addAll(lores, lore);
		setLore(lores);
		return this;
	}
	
	public ItemWrapper addLore(int index, String... lore){
		List<String> lores = getLore();
		lores.addAll(index, Arrays.asList(lore));
		setLore(lores);
		return this;
	}

	public ItemWrapper removeLore(int index){
		List<String> lores = getLore();
		lores.remove(index);
		setLore(lores);
		return this;
	}

	public ItemWrapper removeLore(String regex){
		List<String> lores = getLore();
		for(String s : lores) {
			if(s.matches(regex)) 
				lores.remove(s);
		}
		setLore(lores);
		return this;
	}
	
	public Map<Enchantment, Integer> getEnchantments(){
		return itemStack.getEnchantments();
	}
	
	public boolean hasEnchantment(Enchantment ench){
		return itemStack.containsEnchantment(ench);
	}
	
	public int getEnchantmentLevel(Enchantment ench){
		return itemStack.getEnchantmentLevel(ench);
	}

	public ItemWrapper addEnchantment(Enchantment ench,int level){
		itemStack.addEnchantment(ench, level);
		return this;
	}
	
	public ItemWrapper addUnsafeEnchantment(Enchantment ench,int level){
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemWrapper removeEnchantment(Enchantment ench){
		itemStack.removeEnchantment(ench);
		return this;
	}
	
	public Set<ItemFlag> getItemFlags(){
		return itemStack.getItemMeta().getItemFlags();
	}
	
	public boolean hasItemFlag(ItemFlag flag){
		return itemStack.getItemMeta().hasItemFlag(flag);
	}

	public ItemWrapper addItemFlags(ItemFlag... flags) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(flags);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public ItemWrapper removeItemFlags(ItemFlag... flags) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.removeItemFlags(flags);
		itemStack.setItemMeta(meta);
		return this;
	}
	
	public boolean isUnbreakable(){
		return itemStack.getItemMeta().isUnbreakable();
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
