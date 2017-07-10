package team.unstudio.udpl.api.ui;

import java.util.ArrayList;
import java.util.List;

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
import team.unstudio.udpl.core.UDPLib;

/**
 * 界面的包装类
 */
public class UI implements Listener,Cloneable{
	
	private final Inventory inventory;
	private final List<Slot> slots;
	private boolean allowOperateInventory = false;
	private boolean allowOperateBackpack = false;

	public UI(Inventory inventory) {
		this.inventory = inventory;
		this.slots = new ArrayList<>();
	}

	/**
	 * 获取背包
	 * @return
	 */
	public Inventory getInventory() {
		return inventory;
	}
	
	/**
	 * 打开
	 * @param player
	 * @param plugin
	 */
	public void open(final HumanEntity player){
		inventory.clear();
		for(Slot b:slots) b.paint();
		Bukkit.getPluginManager().registerEvents(this, UDPLib.getInstance());
		player.openInventory(inventory);
	}
	
	/**
	 * 关闭
	 * @param player
	 */
	public void close(Player player){
		if(player.getOpenInventory().getTopInventory().equals(inventory)){
			player.closeInventory();
			if(inventory.getViewers().size()<=0)
				unregisterAllEvent();
		}
	}
	
	/**
	 * 添加槽
	 * @param slot
	 * @return
	 */
	public UI addSlot(Slot slot){
		slots.add(slot);
		slot.setParent(this);
		return this;
	}
	
	/**
	 * 添加槽
	 * @param slot
	 * @return
	 */
	public UI addSlot(Slot slot,Slot ...slots){
		addSlot(slot);
		for(Slot s:slots)
			addSlot(s);
		return this;
	}
	
	/**
	 * 添加槽
	 * @param slot
	 * @return
	 */
	public UI addSlots(Slot slot,int[] slotIDs){
		for(int slotID:slotIDs)
			addSlot(slot.clone().setSlot(slotID));
		return this;
	}
	
	/**
	 * 删除槽
	 * @param slot
	 * @return
	 */
	public boolean removeSlot(Slot slot){
		if(slot==null) return false;
		slots.remove(slot);
		slot.setParent(null);
		return true;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onClick(InventoryClickEvent event){
		if(event.getClickedInventory()!=null&&event.getClickedInventory().equals(inventory)&&event.getSlotType()!=SlotType.OUTSIDE){
			for(Slot b:slots){
				if(b.getSlot()==event.getRawSlot()){
					b.onClick(event);
					if(!b.isAllowOperate()){
						event.setCancelled(true);
						((Player)event.getWhoClicked()).updateInventory();
					}
					return;
				}
			}
		}
		if(event.getSlot()==event.getRawSlot()&&!allowOperateInventory||event.getSlot()!=event.getRawSlot()&&!allowOperateBackpack){
			event.setCancelled(true);
			((Player)event.getWhoClicked()).updateInventory();
		}
	}
	
	@EventHandler(priority=EventPriority.MONITOR,ignoreCancelled=true)
	public void onClose(InventoryCloseEvent event){
		if(event.getInventory().equals(inventory)&&inventory.getViewers().size()<=1)
			unregisterAllEvent();
	}
	
	@Override
	public UI clone(){
		UI ui = null;
		if(inventory.getType()==InventoryType.CHEST){
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

	public boolean isAllowOperateBackpack() {
		return allowOperateBackpack;
	}

	public void setAllowOperateBackpack(boolean allowOperateBackpack) {
		this.allowOperateBackpack = allowOperateBackpack;
	}

	public boolean isAllowOperateInventory() {
		return allowOperateInventory;
	}

	public void setAllowOperateInventory(boolean allowOperateInventory) {
		this.allowOperateInventory = allowOperateInventory;
	}
	
	protected void unregisterAllEvent(){
		InventoryClickEvent.getHandlerList().unregister(this);
		InventoryCloseEvent.getHandlerList().unregister(this);
	}
}
