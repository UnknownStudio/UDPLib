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
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;

import com.google.common.collect.Lists;

public class ItemWrapper {

	public static ItemWrapper newItemWrapper() {
		return new ItemWrapper();
	}

	public static ItemWrapper newItemWrapper(Material type) {
		return new ItemWrapper(type);
	}

	public static ItemWrapper newItemWrapper(Material type, int amount) {
		return new ItemWrapper(type, amount);
	}

	public static ItemWrapper newItemWrapper(Material type, int amount, short damage) {
		return new ItemWrapper(type, amount, damage);
	}

	public static ItemWrapper newItemWrapper(ItemStack itemStack) {
		return new ItemWrapper(itemStack);
	}

	private final ItemStack itemStack;

	public ItemWrapper() {
		this(new ItemStack(Material.AIR));
	}

	public ItemWrapper(Material type) {
		this(new ItemStack(type));
	}

	public ItemWrapper(Material type, int amount) {
		this(new ItemStack(type, amount));
	}

	public ItemWrapper(Material type, int amount, short damage) {
		this(new ItemStack(type, amount, damage));
	}

	public ItemWrapper(ItemStack itemStack) {
		this.itemStack = itemStack.clone();
	}

	public Material getType() {
		return itemStack.getType();
	}

	public ItemWrapper setType(Material type) {
		itemStack.setType(type);
		return this;
	}

	public short getDurability() {
		return itemStack.getDurability();
	}

	public ItemWrapper setDurability(short durability) {
		itemStack.setDurability(durability);
		return this;
	}

	public int getAmount() {
		return itemStack.getAmount();
	}

	public ItemWrapper setAmount(int amount) {
		itemStack.setAmount(amount);
		return this;
	}

	public boolean hasItemMeta() {
		return itemStack.hasItemMeta();
	}

	public String getDisplayName() {
		ItemMeta meta = itemStack.getItemMeta();
		return meta.hasDisplayName() ? meta.getDisplayName() : "";
	}

	public boolean hasDisplayName() {
		return itemStack.getItemMeta().hasDisplayName();
	}

	public ItemWrapper setDisplayName(String name) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

	public String getLocalizedName() {
		ItemMeta meta = itemStack.getItemMeta();
		return meta.hasLocalizedName() ? meta.getLocalizedName() : "";
	}

	public boolean hasLocalizedName() {
		return itemStack.getItemMeta().hasLocalizedName();
	}

	public ItemWrapper setLocalizedName(String name) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLocalizedName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

	public List<String> getLore() {
		ItemMeta meta = itemStack.getItemMeta();
		if (meta.hasLore())
			return meta.getLore();
		else
			return Lists.newArrayList();
	}

	public boolean hasLore() {
		return itemStack.getItemMeta().hasLore();
	}

	public ItemWrapper setLore(String... lore) {
		setLore(Arrays.asList(lore));
		return this;
	}

	public ItemWrapper setLore(List<String> lore) {
		itemStack.getItemMeta().setLore(lore);
		return this;
	}

	public ItemWrapper addLore(String... lore) {
		List<String> lores = getLore();
		Collections.addAll(lores, lore);
		setLore(lores);
		return this;
	}

	public ItemWrapper addLore(int index, String... lore) {
		List<String> lores = getLore();
		lores.addAll(index, Arrays.asList(lore));
		setLore(lores);
		return this;
	}

	public ItemWrapper removeLore(int index) {
		List<String> lores = getLore();
		lores.remove(index);
		setLore(lores);
		return this;
	}

	public ItemWrapper removeLore(String regex) {
		List<String> lores = getLore();
		for (String s : lores) {
			if (s.matches(regex))
				lores.remove(s);
		}
		setLore(lores);
		return this;
	}

	public Map<Enchantment, Integer> getEnchantments() {
		return itemStack.getEnchantments();
	}

	public boolean hasEnchantment(Enchantment ench) {
		return itemStack.containsEnchantment(ench);
	}

	public int getEnchantmentLevel(Enchantment ench) {
		return itemStack.getEnchantmentLevel(ench);
	}

	public ItemWrapper addEnchantment(Enchantment ench, int level) {
		itemStack.addEnchantment(ench, level);
		return this;
	}

	public ItemWrapper addUnsafeEnchantment(Enchantment ench, int level) {
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemWrapper removeEnchantment(Enchantment ench) {
		itemStack.removeEnchantment(ench);
		return this;
	}

	public Set<ItemFlag> getItemFlags() {
		return itemStack.getItemMeta().getItemFlags();
	}

	public boolean hasItemFlag(ItemFlag flag) {
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

	public boolean isUnbreakable() {
		return itemStack.getItemMeta().isUnbreakable();
	}

	public ItemWrapper setUnbreakable(boolean value) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setUnbreakable(value);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemMeta getItemMeta() {
		return itemStack.getItemMeta();
	}

	public ItemWrapper setItemMeta(ItemMeta itemMeta) {
		itemStack.setItemMeta(itemMeta);
		return this;
	}

	public BannerMeta getBannerMeta() {
		return (BannerMeta) getItemMeta();
	}

	public BlockStateMeta getBlockStateMeta() {
		return (BlockStateMeta) getItemMeta();
	}

	public BookMeta getBookMeta() {
		return (BookMeta) getItemMeta();
	}

	public EnchantmentStorageMeta getEnchantmentStorageMeta() {
		return (EnchantmentStorageMeta) getItemMeta();
	}

	public FireworkEffectMeta getFireworkEffectMeta() {
		return (FireworkEffectMeta) getItemMeta();
	}

	public FireworkMeta getFireworkMeta() {
		return (FireworkMeta) getItemMeta();
	}

	public LeatherArmorMeta getLeatherArmorMeta() {
		return (LeatherArmorMeta) getItemMeta();
	}

	public MapMeta getMapMeta() {
		return (MapMeta) getItemMeta();
	}

	public PotionMeta getPotionMeta() {
		return (PotionMeta) getItemMeta();
	}

	public SkullMeta getSkullMeta() {
		return (SkullMeta) getItemMeta();
	}

	public SpawnEggMeta getSpawnEggMeta() {
		return (SpawnEggMeta) getItemMeta();
	}

	public ItemStack get() {
		return itemStack.clone();
	}
}
