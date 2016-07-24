package team.unstudio.udpc.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DefaultButton extends Button{
	
	public DefaultButton() {}
	
	public DefaultButton(ItemStack itemStack,int slot){
		super(itemStack, slot);
	}

	@Override
	public void onClick(InventoryClickEvent event) {}

}
