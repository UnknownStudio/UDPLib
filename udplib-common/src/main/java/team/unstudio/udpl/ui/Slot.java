package team.unstudio.udpl.ui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * 界面槽
 */
public class Slot implements Cloneable{

	private final int slot;
	
	private UI parent;
	private ItemStack itemStack;
	private boolean operable;
	private Consumer<InventoryClickEvent> onClick;
	private Consumer<InventoryClickEvent> onLeftClick;
	private Consumer<InventoryClickEvent> onRightClick;
	private Consumer<InventoryClickEvent> onShiftLeftClick;
	private Consumer<InventoryClickEvent> onShiftRightClick;
	private Consumer<InventoryClickEvent> onDoubleClick;
	private Consumer<InventoryClickEvent> onMiddleClick;
	
	public Slot(int slot){
		this.slot = slot;
	}
	
	public Slot(ItemStack itemStack,int slot){
		this(slot);
		this.itemStack = itemStack;
	}
	
	public void onClick(InventoryClickEvent event){
		if(onClick!=null)
			onClick.accept(event);
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
				onMiddleClick.accept(event);
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
	 */
	public void setItemStack(ItemStack itemstack) {
		this.itemStack = itemstack;
		updateItem();
	}
	
	/**
	 * 获取所在格子
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * 获取父UI
	 */
	public UI getParent() {
		return parent;
	}

	/**
	 * 设置父UI
	 */
	public void setParent(UI parent) {
		this.parent = parent;
		updateItem();
	}

	/**
	 * 是否可以操作
	 */
	public boolean isOperable() {
		return operable;
	}

	/**
	 * 设置是否可以操作
	 */
	public void setOperable(boolean operable) {
		this.operable = operable;
	}
	
	public Consumer<InventoryClickEvent> getOnClick() {
		return onClick;
	}

	public void setOnClick(Consumer<InventoryClickEvent> onClick) {
		this.onClick = onClick;
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
		Slot newSlot = new Slot(getSlot());
		newSlot.setItemStack(getItemStack());
		newSlot.setOperable(isOperable());
		newSlot.setOnClick(getOnClick());
		newSlot.setOnLeftClick(getOnLeftClick());
		newSlot.setOnRightClick(getOnRightClick());
		newSlot.setOnDoubleClick(getOnDoubleClick());
		newSlot.setOnMiddleClick(getOnMiddleClick());
		newSlot.setOnShiftLeftClick(getOnShiftLeftClick());
		newSlot.setOnShiftRightClick(getOnShiftRightClick());
		return newSlot;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Slot))
			return false;
		
		Slot slot = (Slot) obj;
		
		return getSlot() == slot.getSlot();
	}
	
	@Override
	public int hashCode() {
		return getSlot();
	}
}
