package team.unstudio.udpl.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.item.ItemHelper;
import team.unstudio.udpl.util.ReflectionUtils.PackageType;

public final class BookUtils {
	
	private BookUtils(){}

	private static final boolean DEBUG = UDPLib.isDebug();
	private static boolean initialised = false;
	private static Method getHandle;
	private static Method openBook;

	static {
		try {
			getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
			openBook = ReflectionUtils.getMethod("EntityPlayer", PackageType.MINECRAFT_SERVER, "a",
					PackageType.MINECRAFT_SERVER.getClass("ItemStack"),
					PackageType.MINECRAFT_SERVER.getClass("EnumHand"));
			initialised = true;
		} catch (ReflectiveOperationException e) {
			if(DEBUG)
				e.printStackTrace();
			initialised = false;
		}
	}

	public static boolean isInitialised() {
		return initialised;
	}
	
	public static Result open(Player player, ItemStack book){
		if (!isInitialised()) 
			return Result.failure("Uninitialized book api.");
		ItemStack held = player.getInventory().getItemInMainHand();
		player.getInventory().setItemInMainHand(book);
		
		try {
			Object entityplayer = getHandle.invoke(player);
			Class<?> enumHand = PackageType.MINECRAFT_SERVER.getClass("EnumHand");
			Object[] enumArray = enumHand.getEnumConstants();
			openBook.invoke(entityplayer, ItemHelper.getNMSItemStack(book), enumArray[0]);
			player.getInventory().setItemInMainHand(held);
			return Result.success();
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException e) {
			initialised = false;
			player.getInventory().setItemInMainHand(held);
			return Result.failure(e);
		}
	}
	
	public static Result setPages(BookMeta book, BaseComponent... pages){
		return setPages(book, Arrays.stream(pages).map(ComponentSerializer::toString).toArray(String[]::new));
	}
	
	@SuppressWarnings("unchecked")
	public static Result setPages(BookMeta book, String... pages){
		try {
			List<Object> listPages = (List<Object>) ReflectionUtils.getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"),true,"pages").get(book);
			Object ChatSerializer = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("IChatBaseComponent$ChatSerializer").newInstance();
			Method ChatSerializer_a = ReflectionUtils.getMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER
						.getClass("IChatBaseComponent$ChatSerializer"), "a", String.class);
			for (String page : pages)
				listPages.add(ChatSerializer_a.invoke(ChatSerializer, page));
			return Result.success();
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException e) {
			return Result.failure(e);
		}
	}
	

	public static Optional<BaseComponent[]> getPagesReturnBaseComponent(BookMeta book){
		Optional<String[]> pages = getPages(book);
		if(!pages.isPresent())
			return Optional.empty();
		
		return Optional.of(Arrays.stream(pages.get()).map(ComponentSerializer::parse).toArray(BaseComponent[]::new));
	}
	
	@SuppressWarnings("unchecked")
	public static Optional<String[]> getPages(BookMeta book){
		try {
			List<Object> listPages = (List<Object>) ReflectionUtils.getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"),true,"pages").get(book);
			return Optional.of(listPages.stream().map(Object::toString).toArray(String[]::new));
		} catch (NoSuchFieldException | SecurityException | ClassNotFoundException | IllegalArgumentException | IllegalAccessException e) {
			if (DEBUG)
				e.printStackTrace();
		}
		return Optional.empty();
	}
}
