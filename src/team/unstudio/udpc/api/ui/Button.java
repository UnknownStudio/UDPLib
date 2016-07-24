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
	
	/**
	 * 获取物品
	 * @return
	 */
	public ItemStack getItemStack() {
		return itemStack;
	}
	
	/**
	 * 设置物品
	 * @param itemstack
	 */
	public void setItemSstack(ItemStack itemstack) {
		this.itemStack = itemstack;
	}
	
	/**
	 * 获取所在格子
	 * @return
	 */
	public int getSlot() {
		return slot;
	}
	
	/**
	 * 设置所在格子
	 * @param slot
	 */
	public void setSlot(int slot) {
		this.slot = slot;
	}
	
	/**
	 * 点击事件
	 * @param event
	 */
	public abstract void onClick(InventoryClickEvent event);

	/**
	 * 获取父UI
	 * @return
	 */
	public UI getParent() {
		return parent;
	}

	/**
	 * 设置父UI
	 * @param parent
	 */
	public void setParent(UI parent) {
		this.parent = parent;
	}
	
	/**
	 * 绘制
	 */
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
