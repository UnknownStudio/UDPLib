package team.unstudio.udpl.core.nms.v1_11_R1;

import java.util.Map;

import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import team.unstudio.udpl.api.nms.NMSManager;

public class NMSItemStack implements team.unstudio.udpl.api.nms.NMSItemStack{

	@Override
	public Map<String, Object> getTag(ItemStack itemStack) throws Exception {
		return NMSManager.getNMSNBT().toMap(CraftItemStack.asNMSCopy(itemStack).getTag());
	}

	@Override
	public ItemStack setTag(ItemStack itemStack,Map<String, Object> map) throws Exception {
		net.minecraft.server.v1_11_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		nitem.setTag((NBTTagCompound)NMSManager.getNMSNBT().toNBT(map));
		return CraftItemStack.asBukkitCopy(nitem);
	}
	
	@Override
	public boolean hasTag(ItemStack itemStack){
		net.minecraft.server.v1_11_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		return nitem.hasTag();
	}
}
