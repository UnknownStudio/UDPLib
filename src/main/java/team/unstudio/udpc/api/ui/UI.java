package team.unstudio.udpc.api.ui;

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
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

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
	public void open(final HumanEntity player,final JavaPlugin plugin){
		inventory.clear();
		for(Slot b:slots) b.paint();
		Bukkit.getPluginManager().registerEvents(this, plugin);
		player.openInventory(inventory);
	}
	
	/**
	 * 关闭
	 * @param player
	 */
	public void close(Player player){
		if(player.getOpenInventory().getTopInventory().equals(inventory)&&inventory.getViewers().size()==1){
			player.closeInventory();
			unregisterAllListener();
		}
	}
	
	private void unregisterAllListener(){
		InventoryClickEvent.getHandlerList().unregister(this);
		InventoryCloseEvent.getHandlerList().unregister(this);
	}
	
	/**
	 * 添加槽
	 * @param slot
	 * @return
	 */
	public UI addSlot(Slot ...slot){
		for(Slot s:slot){
			slots.add(s);
			s.setParent(this);
		}
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
		if(event.getClickedInventory().equals(inventory)){
			for(Slot b:slots){
				if(b.getSlot()==event.getRawSlot()){
					b.onClick(event);
					if(!b.isCanOperate()){
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
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onClose(InventoryCloseEvent event){
		if(event.getInventory().equals(inventory)&&inventory.getViewers().size()==1){
			unregisterAllListener();
		}
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
}
