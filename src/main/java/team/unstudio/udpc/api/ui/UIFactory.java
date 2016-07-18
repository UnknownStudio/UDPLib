package team.unstudio.udpc.api.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

public class UIFactory {

	public static UI createUI(int size,String title){
		return new UI(Bukkit.createInventory(null,size,title));
	}
	
	public static UI createUI(InventoryType type,String title){
		return new UI(Bukkit.createInventory(null, type, title));
	}
}
