package team.unstudio.udpl.core.nms.asm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.inventory.NmsItemStack;
import team.unstudio.udpl.nms.nbt.NmsNBT;
import team.unstudio.udpl.nms.util.NmsClassLoader;
import team.unstudio.udpl.util.BukkitVersion;

public final class AsmNmsManager {
	
	private final NmsClassLoader classLoader;
	
	private NmsNBT nmsNbt;
	private Constructor<NmsItemStack> nmsItemStackConstructor;

	public AsmNmsManager() {
		classLoader = new NmsClassLoader(AsmNmsManager.class.getClassLoader());
		loadNBT();
		loadItemStack();
	}
	
	public NmsNBT getNmsNBT(){
		return nmsNbt;
	}
	
	public NmsItemStack createNmsItemStack(ItemStack itemStack) {
		try {
			if(nmsItemStackConstructor == null)
				return null;
			return nmsItemStackConstructor.newInstance(itemStack);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			UDPLib.debug(e);
			return null;
		}
	}
	
	private void loadNBT(){
		try {
			nmsNbt = (NmsNBT) loadClass("v1_11_R1/nbt/NmsNBT", BukkitVersion.V1_11_R1, "1.11.2").newInstance();
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			UDPLib.debug(e);
			nmsNbt = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadItemStack(){
		try {
			nmsItemStackConstructor = (Constructor<NmsItemStack>) loadClass("v1_11_R1/inventory/NmsItemStack", BukkitVersion.V1_11_R1, "1.11.2").getDeclaredConstructor(ItemStack.class);
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			UDPLib.debug(e);
			nmsItemStackConstructor = null;
		}
	}
	
	private Class<?> loadClass(String name, BukkitVersion bukkitVersion, String minecraftVersion) throws IOException{
		try (InputStream input = getNmsClassResourceAsStream(name)){
			return classLoader.loadClass(input, bukkitVersion.getPackageName(), minecraftVersion);
		}
	}
	
	private InputStream getNmsClassResourceAsStream(String name){
		return AsmNmsManager.class.getResourceAsStream("/team/unstudio/udpl/core/nms/"+name+".class");
	}
}
