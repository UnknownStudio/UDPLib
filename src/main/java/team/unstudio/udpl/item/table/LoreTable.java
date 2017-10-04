package team.unstudio.udpl.item.table;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public interface LoreTable {
	public List<String> getLore(ItemStack item);
	/**
	 * head of table in lore,
	 * if table does not exist return -1.
	 * 
	 * @param item
	 * @return head of table in lore or -1
	 */
	public int getHead(ItemStack item);
	/**
	 * length of table in lore,
	 * if table does not exist return 0.
	 * 
	 * @param item
	 * @return length of table in lore or 0
	 */
	public int getLength(ItemStack item);
}

