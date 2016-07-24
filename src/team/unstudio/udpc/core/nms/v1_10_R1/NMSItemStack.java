package team.unstudio.udpc.core.nms.v1_10_R1;

import java.util.Map;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.NBTTagCompound;
import team.unstudio.udpc.api.nms.NMSManager;

public class NMSItemStack implements team.unstudio.udpc.api.nms.NMSItemStack{

	@Override
	public Map<String, Object> getNBT(ItemStack itemStack) throws Exception {
		return NMSManager.getNMSNBT().toMap(CraftItemStack.asNMSCopy(itemStack).getTag());
	}

	@Override
	public ItemStack setNBT(ItemStack itemStack,Map<String, Object> map) throws Exception {
		net.minecraft.server.v1_10_R1.ItemStack nitem = CraftItemStack.asNMSCopy(itemStack);
		nitem.setTag((NBTTagCompound)NMSManager.getNMSNBT().toNBT(map));
		return CraftItemStack.asBukkitCopy(nitem);
	}

}