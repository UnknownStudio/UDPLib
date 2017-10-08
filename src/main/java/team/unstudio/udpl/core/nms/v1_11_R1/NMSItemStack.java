package team.unstudio.udpl.core.nms.v1_11_R1;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nbt.NBTTagCompound;
import team.unstudio.udpl.nms.NmsManager;

public class NMSItemStack implements team.unstudio.udpl.nms.NmsItemStack{

	@Override
	public team.unstudio.udpl.nbt.NBTTagCompound getTag(ItemStack itemStack) throws Exception {
		return NmsManager.getNBT().toMap(CraftItemStack.asNMSCopy(itemStack).getTag());
	}

	@Override
	public ItemStack setTag(ItemStack itemStack,NBTTagCompound map) throws Exception {
		net.minecraft.server.v1_11_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		nitem.setTag((net.minecraft.server.v1_11_R1.NBTTagCompound)NmsManager.getNBT().toNBT(map));
		return CraftItemStack.asBukkitCopy(nitem);
	}
	
	@Override
	public boolean hasTag(ItemStack itemStack){
		net.minecraft.server.v1_11_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		return nitem.hasTag();
	}
}
