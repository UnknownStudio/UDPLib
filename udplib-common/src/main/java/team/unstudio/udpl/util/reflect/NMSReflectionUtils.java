package team.unstudio.udpl.util.reflect;

import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.util.reflect.ReflectionUtils.PackageType;

import static team.unstudio.udpl.util.reflect.ReflectionUtils.getField;
import static team.unstudio.udpl.util.reflect.ReflectionUtils.getMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.inventory.ItemStack;

public final class NMSReflectionUtils {

	private static Method CraftPlayer$getHandle;
	private static Field EntityPlayer$locale;
	private static Field CraftMetaBoox$pages;
	private static Method IChatBaseComponent$ChatSerializer$a;
	private static Method CraftItemStack$asNMSCopy;
	private static Method ItemStack$save;
	private static Class<?> NBTTagCompound;
	private static Constructor<?> NBTTagCompound$init;

	public static Method CraftPlayer$getHandle() {
		return CraftPlayer$getHandle;
	}

	public static Field EntityPlayer$locale() {
		return EntityPlayer$locale;
	}

	public static Field CraftMetaBook$pages() {
		return CraftMetaBoox$pages;
	}

	public static Method IChatBaseComponent$ChatSerializer$a() {
		return IChatBaseComponent$ChatSerializer$a;
	}

	public static Method CraftItemStack$asNMSCopy() {
		return CraftItemStack$asNMSCopy;
	}
	
	public static Method ItemStack$save() {
		return ItemStack$save;
	}
	
	public static Class<?> NBTTagCompound() {
		return NBTTagCompound;
	}
	
	public static Constructor<?> NBTTagCompound$init() {
		return NBTTagCompound$init;
	}

	@Init
	private static void init() {
		try {
			CraftPlayer$getHandle = getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle", true);
			EntityPlayer$locale = getField("EntityPlayer", PackageType.MINECRAFT_SERVER, true, "locale");
			CraftMetaBoox$pages = getField("CraftMetaBook", PackageType.CRAFTBUKKIT_INVENTORY, true, "pages");
			IChatBaseComponent$ChatSerializer$a = getMethod("IChatBaseComponent$ChatSerializer",
					ReflectionUtils.PackageType.MINECRAFT_SERVER, "a", true, String.class);
			CraftItemStack$asNMSCopy = getMethod("CraftItemStack", PackageType.CRAFTBUKKIT_INVENTORY, "asNMSCopy", true,
					ItemStack.class);
			NBTTagCompound = PackageType.MINECRAFT_SERVER.getClass("NBTTagCompound");
			NBTTagCompound$init = NBTTagCompound.getConstructor();
			ItemStack$save = getMethod("ItemStack", PackageType.MINECRAFT_SERVER, "save", true, NBTTagCompound);
		} catch (ReflectiveOperationException e) {
			UDPLib.debug(e);
		}
	}
}
