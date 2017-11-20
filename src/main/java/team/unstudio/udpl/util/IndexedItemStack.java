package team.unstudio.udpl.util;

import java.util.Objects;

import org.bukkit.inventory.ItemStack;

public final class IndexedItemStack {
	
	private final ItemStack itemStack;
	private final int index;
	
	public IndexedItemStack(ItemStack itemStack, int index) {
		this.itemStack = itemStack;
		this.index = index;
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public int getIndex() {
		return index;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(!(obj instanceof IndexedItemStack))
			return false;
		
		IndexedItemStack value = (IndexedItemStack) obj;
		if(getIndex() != value.getIndex())
			return false;
		
		if(!getItemStack().equals(value.getIndex()))
			return false;
		
		return true;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(getItemStack(),getIndex());
	}
}
