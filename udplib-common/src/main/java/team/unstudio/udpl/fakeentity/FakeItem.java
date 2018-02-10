package team.unstudio.udpl.fakeentity;

import org.bukkit.inventory.ItemStack;

public class FakeItem extends FakeEntity{
	
	private ItemStack itemStack;
	
	public FakeItem(ItemStack itemStack) {
		this.setItemStack(itemStack);
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	
}
