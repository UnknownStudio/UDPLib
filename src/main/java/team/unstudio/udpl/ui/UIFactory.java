package team.unstudio.udpl.ui;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;

/**
 * 界面的工厂类
 * @author AAA
 *
 */
public interface UIFactory {

	/**
	 * 创建一个界面
	 * @param size 界面大小(9的倍数,即槽数量)
	 * @param title 标题
	 */
	static UI createUI(int size, String title){
		return new UI(Bukkit.createInventory(null,size,title));
	}
	
	/**
	 * 创建一个界面
	 * @param type 界面类型
	 * @param title 标题
	 */
	static UI createUI(InventoryType type, String title){
		return new UI(Bukkit.createInventory(null, type, title));
	}
}
