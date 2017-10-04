package team.unstudio.udpl.item.table;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.item.ItemWrapper;

public class LoreTableManager {
	public static ItemStack addLoreTable(ItemStack item,LoreTable... table) {
		ItemWrapper wrapper = new ItemWrapper(item);
		for(LoreTable t : table) {
			wrapper.addLore(t.getLore(item).toArray(new String[0]));
		}
		return wrapper.get();
	}
	public static ItemStack updateLoreTable(ItemStack item,LoreTable... table) {
		ItemWrapper wrapper = new ItemWrapper(item);
		for(LoreTable t : table) {
			int head = t.getHead(item);
			int length = t.getLength(item);
			for(int i = head;i<length;i++) {
				wrapper.removeLore(i);
			}
			wrapper.addLore(head, t.getLore(item).toArray(new String[0]));
		}
		return wrapper.get();
	}
	public static ItemStack removeLoreTable(ItemStack item,LoreTable... table) {
		ItemWrapper wrapper = new ItemWrapper(item);
		for(LoreTable t : table) {
			int head = t.getHead(item);
			int length = t.getLength(item);
			for(int i = head;i<length;i++) {
				wrapper.removeLore(i);
			}
		}
		return wrapper.get();
	}
	public static boolean hasLoreTable(ItemStack item,LoreTable table) {
		return table.getHead(item)!=-1;
	}
}
