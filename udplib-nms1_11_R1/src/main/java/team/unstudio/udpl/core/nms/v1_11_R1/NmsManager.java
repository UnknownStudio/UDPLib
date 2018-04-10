package team.unstudio.udpl.core.nms.v1_11_R1;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.nms.NmsException;
import team.unstudio.udpl.nms.entity.NmsEntity;
import team.unstudio.udpl.nms.entity.NmsPlayer;
import team.unstudio.udpl.nms.inventory.NmsItemStack;
import team.unstudio.udpl.nms.nbt.NmsNBT;
import team.unstudio.udpl.nms.tileentity.NmsMobSpawner;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;
import team.unstudio.udpl.nms.util.NmsClassLoader;

public class NmsManager implements team.unstudio.udpl.nms.NmsManager{
	
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

	public NmsManager() throws Exception {
		classLoader = new NmsClassLoader(NmsManager.class.getClassLoader());
		loadNmsNBT();
		loadNmsItemStack();
		loadNmsEntity();
		loadNmsTileEntity();
	}
	
	public NmsNBT getNmsNBT(){
		return nmsNbt;
	}
	
	public NmsItemStack createNmsItemStack(ItemStack itemStack) {
		try {
			return newInstance(itemStackConstructor, itemStack);
		} catch (ReflectiveOperationException e) {
			throw new NmsException(e);
		}
	}
	
	public NmsEntity createNmsEntity(Entity entity) {
		try {
			switch (entity.getType()) {
			case PLAYER:
				return newInstance(playerConstructor, entity);
			default:
				return newInstance(entityConstructor, entity);
			}
		} catch (ReflectiveOperationException e) {
			throw new NmsException(e);
		}
	}
	
	public NmsTileEntity createNmsTileEntity(BlockState state) {
		try {
			switch (state.getType()) {
			case MOB_SPAWNER:
				return newInstance(mobSpawnerConstructor, state);
			default:
				return newInstance(tileEntityConstructor, state);
			}
		} catch (ReflectiveOperationException e) {
			throw new NmsException(e);
		}
	}
	
	private void loadNmsNBT() throws Exception{
		nmsNbt = (NmsNBT) loadClass(V1_11_R1+"/nbt/NmsNBT", V1_11_R1, V1_11_2).newInstance();
	}
	
	@SuppressWarnings("unchecked")
	private void loadNmsItemStack() throws Exception{
		itemStackConstructor = (Constructor<NmsItemStack>) getConstructor(V1_11_R1+"/inventory/NmsItemStack", V1_11_R1, V1_11_2, ItemStack.class);
	}
	
	@SuppressWarnings("unchecked")
	private void loadNmsEntity() throws Exception{
		entityConstructor = (Constructor<NmsEntity>) getConstructor(V1_11_R1+"/entity/NmsEntity", V1_11_R1, V1_11_2, Entity.class);
		playerConstructor = (Constructor<NmsPlayer>) getConstructor(V1_11_R1+"/entity/NmsPlayer", V1_11_R1, V1_11_2, Player.class);
	}
	
	@SuppressWarnings("unchecked")
	private void loadNmsTileEntity() throws Exception{
		tileEntityConstructor = (Constructor<NmsTileEntity>) getConstructor(V1_11_R1+"/tileentity/NmsTileEntity", V1_11_R1, V1_11_2, BlockState.class);
		mobSpawnerConstructor = (Constructor<NmsMobSpawner>) getConstructor(V1_11_R1+"/tileentity/NmsMobSpawner", V1_11_R1, V1_11_2, CreatureSpawner.class);
	}
	
	private Class<?> loadClass(String name, String bukkitVersion, String minecraftVersion) throws IOException{
		try (InputStream input = getNmsClassResourceAsStream(name)){
			return classLoader.loadClass(input, bukkitVersion, minecraftVersion);
		}
	}
	
	private InputStream getNmsClassResourceAsStream(String name){
		return NmsManager.class.getResourceAsStream("/team/unstudio/udpl/core/nms/"+name+".class");
	}
	
	private <T> T newInstance(Constructor<T> constructor, Object... args) throws ReflectiveOperationException {
		if (constructor == null)
			return null;
		return constructor.newInstance(args);
	}
	
	private Constructor<?> getConstructor(String name, String bukkitVersion, String minecraftVersion, Class<?>... parameterTypes) throws Exception{
		return loadClass(name, bukkitVersion, minecraftVersion).getDeclaredConstructor(parameterTypes);
	}
}
