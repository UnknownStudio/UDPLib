package team.unstudio.udpl.item.table;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface LoreTable {
	/**
	 * lore of a item.
	 * @param item given item
	 * @return lore list
	 */
	List<String> getLore(ItemStack item);

	/**
	 * head of table in lore,
	 * if table does not exist return -1.
	 * 
	 * @param item item stack
	 * @return head of table in lore or -1
	 */
	int getHead(ItemStack item);

	/**
	 * length of table in lore,
	 * if table does not exist return 0.
	 * 
	 * @param item item stack
	 * @return length of table in lore or 0
	 */
	int getLength(ItemStack item);
}

