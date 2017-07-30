package team.unstudio.udpl.core.nms.v1_11_R1;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.api.nbt.NBTTagCompound;
import team.unstudio.udpl.api.nms.NMSManager;

public class NMSItemStack implements team.unstudio.udpl.api.nms.NMSItemStack{

	@Override
	public team.unstudio.udpl.api.nbt.NBTTagCompound getTag(ItemStack itemStack) throws Exception {
		return NMSManager.getNMSNBT().toMap(CraftItemStack.asNMSCopy(itemStack).getTag());
	}

	@Override
	public ItemStack setTag(ItemStack itemStack,NBTTagCompound map) throws Exception {
		net.minecraft.server.v1_11_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		nitem.setTag((net.minecraft.server.v1_11_R1.NBTTagCompound)NMSManager.getNMSNBT().toNBT(map));
		return CraftItemStack.asBukkitCopy(nitem);
	}
	
	@Override
	public boolean hasTag(ItemStack itemStack){
		net.minecraft.server.v1_11_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		return nitem.hasTag();
	}
}
