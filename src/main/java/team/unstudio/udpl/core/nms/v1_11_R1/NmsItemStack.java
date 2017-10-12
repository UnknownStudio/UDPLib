package team.unstudio.udpl.core.nms.v1_11_R1;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.nbt.NBTTagCompound;

public class NmsItemStack implements team.unstudio.udpl.nms.inventory.NmsItemStack{
	
	private final net.minecraft.server.v1_11_R1.ItemStack itemStack;
	
	public NmsItemStack(ItemStack itemStack) {
		this.itemStack = CraftItemStack.asNMSCopy(itemStack);
	}

	@Override
	public ItemStack getItemStack() {
		return CraftItemStack.asBukkitCopy(itemStack);
	}

	@Override
	public NBTTagCompound getTag() {
		return NmsHelper.getNBT().toCompound(itemStack.getTag());
	}

	@Override
	public void setTag(NBTTagCompound nbt) {
		itemStack.setTag((net.minecraft.server.v1_11_R1.NBTTagCompound)NmsHelper.getNBT().toNBT(nbt));
	}

	@Override
	public boolean hasTag() {
		return itemStack.hasTag();
	}
	
	@Override
	public void load(NBTTagCompound nbt){
		itemStack.load((net.minecraft.server.v1_11_R1.NBTTagCompound)NmsHelper.getNBT().toNBT(nbt));
	}

	@Override
	public NBTTagCompound save() {
		return NmsHelper.getNBT().toCompound(itemStack.save(new net.minecraft.server.v1_11_R1.NBTTagCompound()));
	}
}
