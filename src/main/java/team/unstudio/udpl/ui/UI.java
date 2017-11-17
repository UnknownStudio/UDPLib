package team.unstudio.udpl.ui;

import java.util.Set;
import java.util.function.BiConsumer;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Sets;

import team.unstudio.udpl.core.UDPLib;

/**
 * 界面的包装类
 */
public class UI implements Cloneable{

	private final Inventory inventory;
	private final Set<Slot> slots;

	private final UIListener listener = new UIListener();
	
	private boolean allowOperateInventory = false;
	private boolean allowOperateBackpack = false;
	private BiConsumer<UI, HumanEntity> onOpen;
	private BiConsumer<UI, HumanEntity> onClose;


	public UI(Inventory inventory) {
		this.inventory = inventory;
		this.slots = Sets.newHashSet();
	}
	
	public UI(InventoryType type) {
		this(Bukkit.createInventory(null, type));
	}
	
	public UI(int size) {
		this(Bukkit.createInventory(null, size));
	}
	
	public UI(InventoryType type, String title) {
		this(Bukkit.createInventory(null, type, title));
	}
	
	public UI(int size, String title) {
		this(Bukkit.createInventory(null, size, title));
	}

	/**
	 * 获取界面
	 */
	public final Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * 打开
	 */
	public void open(HumanEntity player){
		inventory.clear();
		for(Slot b:slots) 
			b.updateItem();
		callOnOpen(player);
		listener.registerListener();
		player.openInventory(inventory);
	}
	
	/**
	 * 关闭
	 */
	public void close(HumanEntity player){
		if(player.getOpenInventory().getTopInventory().equals(inventory))
			player.closeInventory();
	}
	
	/**
	 * 添加槽
	 */
	public UI addSlot(@Nonnull Slot slot){
		Validate.notNull(slot);
		slots.add(slot);
		slot.setParent(this);
		return this;
	}
	
	/**
	 * 添加槽
	 */
	public UI addSlot(@Nonnull Slot slot,Slot... slots){
		addSlot(slot);
		for(Slot s:slots)
			addSlot(s);
		return this;
	}
	
	/**
	 * 添加槽
	 */
	public UI addSlots(@Nonnull Slot slot,int... slotIDs){
		Validate.notNull(slot);
		for(int slotID : slotIDs){
			Slot newSlot = slot.clone();
			newSlot.setSlot(slotID);
			addSlot(newSlot);
		}
		return this;
	}
	
	/**
	 * 添加槽
	 */
	public UI addSlots(@Nonnull ItemStack itemStack,int... slotIDs){
		Validate.notNull(itemStack);
		for(int slotID : slotIDs){
			Slot newSlot = new Slot(itemStack.clone(), slotID);
			addSlot(newSlot);
		}
		return this;
	}
	
	/**
	 * 删除槽
	 */
	public void removeSlot(Slot slot){
		if(slot == null)
			return;
		slots.remove(slot);
		slot.setParent(null);
	}

	/**
	 * 是否允许玩家操作背包
	 */
	public boolean isAllowOperateBackpack() {
		return allowOperateBackpack;
	}

	/**
	 * 设置是否允许玩家操作背包
	 */
	public void setAllowOperateBackpack(boolean allowOperateBackpack) {
		this.allowOperateBackpack = allowOperateBackpack;
	}

	/**
	 * 是否允许玩家操作界面
	 */
	public boolean isAllowOperateInventory() {
		return allowOperateInventory;
	}

	/**
	 * 设置是否允许玩家操作界面
	 */
	public void setAllowOperateInventory(boolean allowOperateInventory) {
		this.allowOperateInventory = allowOperateInventory;
	}
	
	/**
	 * 获取界面打开监听器
	 */
	public BiConsumer<UI, HumanEntity> getOnOpen() {
		return onOpen;
	}

	/**
	 * 设置界面打开监听器
	 */
	public void setOnOpen(BiConsumer<UI, HumanEntity> onOpen) {
		this.onOpen = onOpen;
	}

	/**
	 * 设置界面关闭监听器
	 */
	public BiConsumer<UI, HumanEntity> getOnClose() {
		return onClose;
	}

	/**
	 * 设置界面关闭监听器
	 */
	public void setOnClose(BiConsumer<UI, HumanEntity> onClose) {
		this.onClose = onClose;
	}
	
	private void callOnOpen(HumanEntity player){
		if(onOpen!=null)
			onOpen.accept(this, player);
	}
	
	private void callOnClose(HumanEntity player){
		if(onClose!=null)
			onClose.accept(this, player);
	}

	@Override
	public UI clone(){
		UI ui;
		if(inventory.getType() == InventoryType.CHEST){
			ui = new UI(Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), inventory.getTitle()));
			for(Slot b:slots){
				ui.addSlot(b.clone());
			}
		}else{
			ui = new UI(Bukkit.createInventory(inventory.getHolder(), inventory.getType(), inventory.getTitle()));
			for(Slot b:slots){
				ui.addSlot(b.clone());
			}
		}
		return ui;
	}
	
	private class UIListener implements Listener{
		private boolean registedListener = false;
		
		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		public void onClick(InventoryClickEvent event){
			if(event.getClickedInventory()!=null&&event.getClickedInventory().equals(inventory)&&event.getSlotType()!=SlotType.OUTSIDE){
				for(Slot b:slots){
					if(b.getSlot()!=event.getRawSlot())
						continue;
					
					b.onClick(event);
					
					if (!b.isOperable()) {
						event.setCancelled(true);
						((Player) event.getWhoClicked()).updateInventory();
					}
					
					return;
				}
			}
			if(event.getSlot()==event.getRawSlot()&&!allowOperateInventory||event.getSlot()!=event.getRawSlot()&&!allowOperateBackpack){
				event.setCancelled(true);
				((Player)event.getWhoClicked()).updateInventory();
			}
		}

		@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
		public void onClose(InventoryCloseEvent event){
			if(!event.getInventory().equals(inventory))
				return;
			
			callOnClose(event.getPlayer());
			
			if(inventory.getViewers().size()>1)
				return;
			
			unregisterListener();
		}
		
		private void registerListener(){
			if(registedListener)
				return;
			
			registedListener = true;
			Bukkit.getPluginManager().registerEvents(this, UDPLib.getInstance());
		}

		private void unregisterListener(){
			if(!registedListener)
				return;
			
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryCloseEvent.getHandlerList().unregister(this);
			registedListener = false;
		}
	}
}
