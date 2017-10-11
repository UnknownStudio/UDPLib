package team.unstudio.udpl.core.nms.asm;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.NmsNBT;
import team.unstudio.udpl.nms.ReflectionUtils;
import team.unstudio.udpl.nms.inventory.NmsItemStack;

public class AsmNmsManager {
	
	private static final boolean DEBUG = UDPLib.isDebug();
	
	public static final String NMS_VERSION = ReflectionUtils.PackageType.getServerVersion();
	
	private final DynamicClassLoader classLoader;
	private NmsNBT nmsNbt;
	private Constructor<NmsItemStack> nmsItemStackConstructor;

	public AsmNmsManager() {
		classLoader = new DynamicClassLoader(AsmNmsManager.class.getClassLoader());
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
			if(DEBUG)
				e.printStackTrace();
			return null;
		}
	}
	
	private void loadNBT(){
		try {
			byte[] b = NmsNBTGenerator.generate(NMS_VERSION);
			nmsNbt = (NmsNBT) classLoader.loadBytecode("team.unstudio.udpl.core.nms.asm.NmsNBT", b, 0, b.length).newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			if(DEBUG)
				e.printStackTrace();
			nmsNbt = null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadItemStack(){
		try {
			byte[] b = NmsItemStackGenerator.generate(NMS_VERSION);
			nmsItemStackConstructor = (Constructor<NmsItemStack>) classLoader.loadBytecode("team.unstudio.udpl.core.nms.asm.NmsItemStack", b, 0, b.length).getDeclaredConstructor(ItemStack.class);
		} catch (NoSuchMethodException | SecurityException e) {
			if(DEBUG)
				e.printStackTrace();
			nmsItemStackConstructor = null;
		}
	}
}
