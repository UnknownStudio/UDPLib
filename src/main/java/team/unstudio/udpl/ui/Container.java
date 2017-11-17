package team.unstudio.udpl.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Container implements Cloneable,ConfigurationSerializable{
	
	private final Inventory inventory;
	
	public Container(@Nonnull Inventory inventory) {
		Validate.notNull(inventory);
		this.inventory = inventory;
	}
	
	public Container(InventoryType type) {
		this(Bukkit.createInventory(null, type));
	}
	
	public Container(int size) {
		this(Bukkit.createInventory(null, size));
	}
	
	public Container(InventoryType type, String title) {
		this(Bukkit.createInventory(null, type, title));
	}
	
	public Container(int size, String title) {
		this(Bukkit.createInventory(null, size, title));
	}
	
	public final Inventory getInventory(){
		return inventory;
	}
	
	public void addItem(ItemStack item, int... indexs){
		for(int index : indexs)
			inventory.setItem(index, item.clone());
	}
	
	public final void open(HumanEntity player){
		player.openInventory(inventory);
	}
	
	@Override
	public Container clone(){
		Container container;
		if(inventory.getType() == InventoryType.CHEST){
			container = new Container(Bukkit.createInventory(inventory.getHolder(), inventory.getSize(), inventory.getTitle()));
		}else{
			container = new Container(Bukkit.createInventory(inventory.getHolder(), inventory.getType(), inventory.getTitle()));
		}
		container.getInventory().setContents(inventory.getContents());
		return container;
	}
	
	@Override
	public Map<String, Object> serialize() {
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("type", inventory.getType().name());
		map.put("title", inventory.getTitle());
		map.put("size", inventory.getSize());
		List<ItemStack> items = Lists.newLinkedList();
		Arrays.stream(inventory.getContents()).forEach(items::add);
		map.put("items", items);
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Container deserialize(Map<String, Object> map){
		InventoryType inventoryType = InventoryType.valueOf((String) map.get("type"));
		String title = (String) map.get("title");
		List<ItemStack> items = (List<ItemStack>) map.get("items");
		
		Container container;
		if(inventoryType == InventoryType.CHEST){
			container = new Container((int) map.get("size"), title);
		}else{
			container = new Container(inventoryType, title);
		}
		
		for(int i = 0, size = items.size();i < size;i++){
			container.getInventory().setItem(i, items.get(i));
		}
		
		return container;
	}
}
