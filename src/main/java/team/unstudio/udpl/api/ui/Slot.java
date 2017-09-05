package team.unstudio.udpl.api.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 界面槽
 */
public abstract class Slot implements Cloneable{
	
	private UI parent;
	private ItemStack itemStack;
	private int slot;
	private boolean allowOperate;
	
	public Slot() {}
	
	public Slot(int slot){
		this.slot = slot;
	}
	
	public Slot(ItemStack itemStack,int slot){
		this(slot);
		this.itemStack = itemStack;
	}
	
	/**
	 * 获取物品
	 * @return
	 */
	public ItemStack getItemStack() {
		return parent!=null?parent.getInventory().getItem(slot):itemStack;
	}
	
	/**
	 * 设置物品
	 * @param itemstack
	 */
	public Slot setItemSstack(ItemStack itemstack) {
		this.itemStack = itemstack;
		if(parent!=null)parent.getInventory().setItem(slot, itemstack);
		return this;
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
	public Slot setSlot(int slot) {
		this.slot = slot;
		if(parent!=null)parent.getInventory().setItem(slot, itemStack);
		return this;
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
	public final UI getParent() {
		return parent;
	}

	/**
	 * 设置父UI
	 * @param parent
	 */
	public final void setParent(UI parent) {
		this.parent = parent;
		if(parent!=null)parent.getInventory().setItem(slot, itemStack);
	}
	
	/**
	 * 绘制
	 */
	public void paint(){
		parent.getInventory().setItem(slot, itemStack);
	}
	
	@Override
	public Slot clone(){
		Slot button = null;
		try {
			button = (Slot) super.clone();
			button.itemStack = itemStack.clone();
			button.slot = slot;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return button;
	}

	/**
	 * 是否可以操作
	 * @return
	 */
	public boolean isAllowOperate() {
		return allowOperate;
	}

	/**
	 * 设置是否可以操作
	 * @param allowOperate
	 */
	public Slot setAllowOperate(boolean allowOperate) {
		this.allowOperate = allowOperate;
		return this;
	}
}
