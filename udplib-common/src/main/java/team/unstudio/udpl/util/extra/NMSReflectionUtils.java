package team.unstudio.udpl.util.extra;

import team.unstudio.udpl.util.ReflectionUtils;
import team.unstudio.udpl.util.ReflectionUtils.PackageType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;

import static team.unstudio.udpl.util.ReflectionUtils.getField;
import static team.unstudio.udpl.util.ReflectionUtils.getMethod;

public interface NMSReflectionUtils {

	AtomicReference<Method> CRAFT_PLAYER_GET_HANDLE = new AtomicReference<>();
	static Method getHandleNMS(){
		if(CRAFT_PLAYER_GET_HANDLE.get() ==null) {
			try {
				CRAFT_PLAYER_GET_HANDLE.set(getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle"));
			} catch (NoSuchMethodException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return CRAFT_PLAYER_GET_HANDLE.get();
	}
	
	AtomicReference<Field> ENTITY_PLAYER_LOCALE = new AtomicReference<>();
	static Field getLocaleNMS() {
		if(ENTITY_PLAYER_LOCALE.get() == null){
			try {
				ENTITY_PLAYER_LOCALE.set(getField(PackageType.MINECRAFT_SERVER.getClass("EntityPlayer"), true, "locale"));
			} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return ENTITY_PLAYER_LOCALE.get();
	}
	
	AtomicReference<Field> CRAFT_META_BOOK_PAGES = new AtomicReference<>();
	static Field getCraftMetaBookPages() {
		if(CRAFT_META_BOOK_PAGES.get() == null) {
			try {
				CRAFT_META_BOOK_PAGES.set(ReflectionUtils.getField(PackageType.CRAFTBUKKIT_INVENTORY.getClass("CraftMetaBook"),true,"pages"));
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		return CRAFT_META_BOOK_PAGES.get();
	}
	
	AtomicReference<Method> IChatBaseComponent$ChatSerializer$a = new AtomicReference<>();
	static Method getIChatBaseComponent$ChatSerializer$a() {
		if(IChatBaseComponent$ChatSerializer$a.get() == null) {
			try {
				IChatBaseComponent$ChatSerializer$a.set(ReflectionUtils.getMethod(ReflectionUtils.PackageType.MINECRAFT_SERVER
						.getClass("IChatBaseComponent$ChatSerializer"), "a", String.class));
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
		}
		return IChatBaseComponent$ChatSerializer$a.get();
	}
}
