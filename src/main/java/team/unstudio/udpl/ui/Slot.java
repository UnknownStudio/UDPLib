package team.unstudio.udpl.ui;

import java.util.function.Consumer;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 界面槽
 */
public class Slot implements Cloneable{

	private UI parent;
	private ItemStack itemStack;
	private int slot;
	private boolean operable;
	private Consumer<InventoryClickEvent> onLeftClick;
	private Consumer<InventoryClickEvent> onRightClick;
	private Consumer<InventoryClickEvent> onShiftLeftClick;
	private Consumer<InventoryClickEvent> onShiftRightClick;
	private Consumer<InventoryClickEvent> onDoubleClick;
	private Consumer<InventoryClickEvent> onMiddleClick;
	
	public Slot() {}
	
	public Slot(int slot){
		this.slot = slot;
	}
	
	public Slot(ItemStack itemStack,int slot){
		this(slot);
		this.itemStack = itemStack;
	}
	
	public void onClick(InventoryClickEvent event){
		switch (event.getClick()) {
		case LEFT:
			if(onLeftClick != null)
				onLeftClick.accept(event);
			break;
		case RIGHT:
			if(onRightClick != null)
				onRightClick.accept(event);
			break;
		case SHIFT_LEFT:
			if(onShiftLeftClick != null)
				onShiftLeftClick.accept(event);
			break;
		case SHIFT_RIGHT:
			if(onShiftRightClick != null)
				onShiftRightClick.accept(event);
			break;
		case DOUBLE_CLICK:
			if(onDoubleClick != null)
				onDoubleClick.accept(event);
			break;
		case MIDDLE:
			if(onMiddleClick != null)
				onDoubleClick.accept(event);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 获取物品
	 */
	public ItemStack getItemStack() {
		return parent != null ? parent.getInventory().getItem(slot) : itemStack;
	}
	
	/**
	 * 设置物品
	 * @param itemstack
	 */
	public void setItemStack(ItemStack itemstack) {
		this.itemStack = itemstack;
		updateItem();
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
		updateItem();
	}

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
		updateItem();
	}

	/**
	 * 是否可以操作
	 * @return
	 */
	public boolean isOperable() {
		return operable;
	}

	/**
	 * 设置是否可以操作
	 * @param operable
	 */
	public void setOperable(boolean operable) {
		this.operable = operable;
	}
	
	public Consumer<InventoryClickEvent> getOnLeftClick() {
		return onLeftClick;
	}

	public void setOnLeftClick(Consumer<InventoryClickEvent> onLeftClick) {
		this.onLeftClick = onLeftClick;
	}

	public Consumer<InventoryClickEvent> getOnRightClick() {
		return onRightClick;
	}

	public void setOnRightClick(Consumer<InventoryClickEvent> onRightClick) {
		this.onRightClick = onRightClick;
	}

	public Consumer<InventoryClickEvent> getOnShiftLeftClick() {
		return onShiftLeftClick;
	}

	public void setOnShiftLeftClick(Consumer<InventoryClickEvent> onShiftLeftClick) {
		this.onShiftLeftClick = onShiftLeftClick;
	}

	public Consumer<InventoryClickEvent> getOnShiftRightClick() {
		return onShiftRightClick;
	}

	public void setOnShiftRightClick(Consumer<InventoryClickEvent> onShiftRightClick) {
		this.onShiftRightClick = onShiftRightClick;
	}

	public Consumer<InventoryClickEvent> getOnDoubleClick() {
		return onDoubleClick;
	}

	public void setOnDoubleClick(Consumer<InventoryClickEvent> onDoubleClick) {
		this.onDoubleClick = onDoubleClick;
	}

	public Consumer<InventoryClickEvent> getOnMiddleClick() {
		return onMiddleClick;
	}

	public void setOnMiddleClick(Consumer<InventoryClickEvent> onMiddleClick) {
		this.onMiddleClick = onMiddleClick;
	}

	protected void updateItem(){
		if(parent!=null)
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
}
