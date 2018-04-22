package team.unstudio.udpl.item;

import static team.unstudio.udpl.util.reflect.NMSReflectionUtils.*;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.UDPLib;
import javax.annotation.Nullable;

public interface ItemHelper {
	   
    /**
     * 转换为JSON格式
     * @param itemStack 物品
     */
    @Nullable
	static String toJson(ItemStack itemStack) {
		try {
			return ItemStack$save()
					.invoke(CraftItemStack$asNMSCopy().invoke(null, itemStack), NBTTagCompound$init().newInstance())
					.toString();
		} catch (ReflectiveOperationException e) {
			UDPLib.debug(e);
		}
		return null;
    }
}
