package team.unstudio.udpl.item;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.util.ReflectionUtils;
import team.unstudio.udpl.util.ReflectionUtils.PackageType;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public interface ItemHelper {
	
	static Optional<Object> getNMSItemStack(ItemStack item) {
		try {
			Method asNMSCopy = ReflectionUtils.getMethod(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack"),
					"asNMSCopy", ItemStack.class);
			return Optional.ofNullable(asNMSCopy.invoke(null, item));
		} catch (NoSuchMethodException | ClassNotFoundException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			UDPLib.debug(e);
		}
		return Optional.empty();
	}
	   
    /**
     * 转换为JSON格式
     * @param itemStack 物品
     */
    @Nonnull
	static String toJson(ItemStack itemStack){
		try {
			Class<?> ccitemstack = ReflectionUtils.PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftItemStack");
			Class<?> citemstack = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("ItemStack");
			Class<?> cmap = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
			Object nbt = cmap.newInstance();
			Method asnmscopy = ccitemstack.getDeclaredMethod("asNMSCopy", ItemStack.class);
			asnmscopy.setAccessible(true);
			Method save = citemstack.getDeclaredMethod("save", cmap);
			save.setAccessible(true);
			return save.invoke(asnmscopy.invoke(null, itemStack), nbt).toString();
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			UDPLib.debug(e);
		}
		return "";
    }
}
