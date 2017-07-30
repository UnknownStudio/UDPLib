package team.unstudio.udpl.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 提供默认实现的槽
 */
public class DefaultSlot extends Slot{
	
	public DefaultSlot() {}
	
	public DefaultSlot(ItemStack itemStack,int slot){
		super(itemStack, slot);
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

}
