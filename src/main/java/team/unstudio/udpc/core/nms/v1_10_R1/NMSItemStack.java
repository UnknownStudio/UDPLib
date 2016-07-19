package team.unstudio.udpc.core.nms.v1_10_R1;

import org.bukkit.craftbukkit.v1_10_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NMSItemStack {

	public void getCraftItemStack(ItemStack item){
		net.minecraft.server.v1_10_R1.ItemStack nitem = CraftItemStack.asNMSCopy(item);
	}
}
