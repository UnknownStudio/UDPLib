package team.unstudio.udpc.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 
 * 一个默认的按钮
 * 
 * @author AAA
 *
 */
public class DefaultSlot extends Slot{
	
	public DefaultSlot() {}
	
	public DefaultSlot(ItemStack itemStack,int slot){
		super(itemStack, slot);
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

}
