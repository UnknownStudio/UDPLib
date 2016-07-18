package team.unstudio.udpc.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public abstract class Button implements Cloneable{
	
	private UI parent;
	private ItemStack itemStack;
	private int slot;
	
	public Button() {}
	
	public Button(ItemStack itemStack,int slot){
		this.itemStack = itemStack;
		this.slot = slot;
	}
	
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	public void setItemSstack(ItemStack itemstack) {
		this.itemStack = itemstack;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	public abstract void onClick(InventoryClickEvent event);

	public UI getParent() {
		return parent;
	}

	public void setParent(UI parent) {
		this.parent = parent;
	}
	
	public void paint(){
		parent.getInventory().setItem(slot, itemStack);
	}
	
	@Override
	public Button clone(){
		Button button = null;
		try {
			button = (Button) super.clone();
			button.itemStack = itemStack.clone();
			button.slot = slot;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return button;
	}
}
