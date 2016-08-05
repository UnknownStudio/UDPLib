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
	private final List<Button> buttons;
	private boolean canOperate = false;
	
	public boolean isCanOperate() {
		return canOperate;
	}

	public void setCanOperate(boolean canOperate) {
		this.canOperate = canOperate;
	}

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
		Bukkit.getPluginManager().registerEvents(UI.this, plugin);
		player.openInventory(inventory);
	}
	
	/**
	 * 关闭
	 * @param player
	 */
	public void close(Player player){
		if(player.getOpenInventory().getTopInventory().equals(inventory)){
			unregisterAllListener();
			player.closeInventory();
		}
	}
	
	private void unregisterAllListener(){
		InventoryClickEvent.getHandlerList().unregister(this);
		InventoryCloseEvent.getHandlerList().unregister(this);
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
					if(!b.isCanOperate())event.setCancelled(true);
					((Player)event.getWhoClicked()).updateInventory();
					return;
				}
			}
		}
		if(!canOperate) event.setCancelled(true);
		((Player)event.getWhoClicked()).updateInventory();
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onClose(InventoryCloseEvent event){
		if(event.getInventory().equals(inventory)){
			unregisterAllListener();
		}
	}
	
	@Override
	public UI clone(){
		UI ui = null;
		if(inventory.getType()==InventoryType.CHEST){
			ui = new UI(Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), inventory.getTitle()));
			for(Button b:buttons){
				ui.addButton(b.clone());
			}
		}else{
			ui = new UI(Bukkit.createInventory(inventory.getHolder(), inventory.getType(), inventory.getTitle()));
			for(Button b:buttons){
				ui.addButton(b.clone());
			}
		}
		return ui;
	}
}
