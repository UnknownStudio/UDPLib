package team.unstudio.udpc.api.ui;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class UI implements Listener,Cloneable{
	
	private final Inventory inventory;
	private final List<Button> buttons;
	
	public UI(Inventory inventory) {
		this.inventory = inventory;
		this.buttons = new ArrayList<>();
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
		for(Button b:buttons) b.paint();
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				Bukkit.getPluginManager().registerEvents(UI.this, plugin);
				player.openInventory(inventory);
			}
		}, 1L);
	}
	
	/**
	 * 关闭
	 * @param player
	 */
	public void close(HumanEntity player){
		if(player.getOpenInventory().getTopInventory().equals(inventory)){
			InventoryClickEvent.getHandlerList().unregister(this);
			InventoryDragEvent.getHandlerList().unregister(this);
			InventoryMoveItemEvent.getHandlerList().unregister(this);
			player.getOpenInventory().close();
		}
	}
	
	/**
	 * 添加按钮
	 * @param button
	 * @return
	 */
	public boolean addButton(Button button){
		if(button==null) return false;
		buttons.add(button);
		button.setParent(this);
		return true;
	}
	
	/**
	 * 删除按钮
	 * @param button
	 * @return
	 */
	public boolean removeButton(Button button){
		if(button==null) return false;
		buttons.remove(button);
		button.setParent(null);
		return true;
	}

	@EventHandler(priority=EventPriority.HIGHEST)
	public void onClick(InventoryClickEvent event){
		if(event.getClickedInventory().equals(inventory)){
			for(Button b:buttons){
				if(b.getSlot()==event.getRawSlot()){
					b.onClick(event);
					event.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onDrag(InventoryDragEvent event){
		if(event.getInventory().equals(inventory))event.setCancelled(true);
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onMoveItem(InventoryMoveItemEvent event){
		if(event.getSource().equals(inventory))event.setCancelled(true);
	}
	
	@Override
	public UI clone(){
		UI ui = null;
		if(inventory.getType()==InventoryType.CHEST){
			ui = new UI(Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), inventory.getTitle()));
		}else{
			ui = new UI(Bukkit.createInventory(inventory.getHolder(), inventory.getType(), inventory.getTitle()));
		}
		for(Button b:buttons){
			ui.addButton(b.clone());
		}
		return ui;
	}
}
