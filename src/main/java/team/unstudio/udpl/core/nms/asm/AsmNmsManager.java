package team.unstudio.udpl.core.nms.asm;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.entity.NmsEntity;
import team.unstudio.udpl.nms.entity.NmsPlayer;
import team.unstudio.udpl.nms.inventory.NmsItemStack;
import team.unstudio.udpl.nms.nbt.NmsNBT;
import team.unstudio.udpl.nms.tileentity.NmsMobSpawner;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;
import team.unstudio.udpl.nms.util.NmsClassLoader;

public final class AsmNmsManager {
	
	public static final String V1_11_R1 = "v1_11_R1";
	public static final String V1_11_2 = "1.11.2";
	
	private final NmsClassLoader classLoader;
	
	private NmsNBT nmsNbt;
	
	private Constructor<NmsItemStack> itemStackConstructor;
	
	// nms entities
	private Constructor<NmsEntity> entityConstructor;
	private Constructor<NmsPlayer> playerConstructor;
	
	// nms tileentities
	private Constructor<NmsTileEntity> tileEntityConstructor;
	private Constructor<NmsMobSpawner> mobSpawnerConstructor;

	public AsmNmsManager() {
		classLoader = new NmsClassLoader(AsmNmsManager.class.getClassLoader());
		loadNmsNBT();
		loadNmsItemStack();
		loadNmsEntity();
		loadNmsTileEntity();
	}
	
	public NmsNBT getNmsNBT(){
		return nmsNbt;
	}
	
	public NmsItemStack createNmsItemStack(ItemStack itemStack) {
		return newInstance(itemStackConstructor, itemStack);
	}
	
	public NmsEntity createNmsEntity(Entity entity){
		switch (entity.getType()) {
		case PLAYER:
			return newInstance(playerConstructor, entity);
		default:
			return newInstance(entityConstructor, entity);
		}
	}
	
	public NmsTileEntity createNmsTileEntity(BlockState state){
		switch (state.getType()) {
		case MOB_SPAWNER:
			return newInstance(mobSpawnerConstructor, state);
		default:
			return newInstance(tileEntityConstructor, state);
		}
	}
	
	private void loadNmsNBT(){
		try {
			nmsNbt = (NmsNBT) loadClass(V1_11_R1+"/nbt/NmsNBT", V1_11_R1, V1_11_2).newInstance();
		} catch (InstantiationException | IllegalAccessException | IOException e) {
			UDPLib.debug(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void loadNmsItemStack(){
		itemStackConstructor = (Constructor<NmsItemStack>) getConstructor(V1_11_R1+"/inventory/NmsItemStack", V1_11_R1, V1_11_2, ItemStack.class);
	}
	
	@SuppressWarnings("unchecked")
	private void loadNmsEntity(){
		entityConstructor = (Constructor<NmsEntity>) getConstructor(V1_11_R1+"/entity/NmsEntity", V1_11_R1, V1_11_2, Entity.class);
		playerConstructor = (Constructor<NmsPlayer>) getConstructor(V1_11_R1+"/entity/NmsPlayer", V1_11_R1, V1_11_2, Player.class);
	}
	
	@SuppressWarnings("unchecked")
	private void loadNmsTileEntity(){
		tileEntityConstructor = (Constructor<NmsTileEntity>) getConstructor(V1_11_R1+"/tileentity/NmsTileEntity", V1_11_R1, V1_11_2, BlockState.class);
		mobSpawnerConstructor = (Constructor<NmsMobSpawner>) getConstructor(V1_11_R1+"/tileentity/NmsMobSpawner", V1_11_R1, V1_11_2, CreatureSpawner.class);
	}
	
	private Class<?> loadClass(String name, String bukkitVersion, String minecraftVersion) throws IOException{
		try (InputStream input = getNmsClassResourceAsStream(name)){
			return classLoader.loadClass(input, bukkitVersion, minecraftVersion);
		}
	}
	
	private InputStream getNmsClassResourceAsStream(String name){
		return AsmNmsManager.class.getResourceAsStream("/team/unstudio/udpl/core/nms/"+name+".class");
	}
	
	private <T> T newInstance(Constructor<T> constructor, Object... args){
		try {
			if(constructor == null)
				return null;
			return constructor.newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			UDPLib.debug(e);
			return null;
		}
	}
	
	private Constructor<?> getConstructor(String name, String bukkitVersion, String minecraftVersion, Class<?>... classes){
		try {
			return loadClass(name, bukkitVersion, minecraftVersion).getDeclaredConstructor(ItemStack.class);
		} catch (NoSuchMethodException | SecurityException | IOException e) {
			UDPLib.debug(e);
			return null;
		}
	}
}
