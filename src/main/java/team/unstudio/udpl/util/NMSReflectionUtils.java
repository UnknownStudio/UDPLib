package team.unstudio.udpl.util;

import static team.unstudio.udpl.util.ReflectionUtils.*;
import java.lang.reflect.*;

import team.unstudio.udpl.util.ReflectionUtils.PackageType;

public final class NMSReflectionUtils {
	
	private NMSReflectionUtils(){}

	private static Method CRAFT_PLAYER_GET_HANDLE;
	public static Method getCraftPlayer_getHandle(){
		if(CRAFT_PLAYER_GET_HANDLE==null){
			try {
				CRAFT_PLAYER_GET_HANDLE = getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
			} catch (NoSuchMethodException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return CRAFT_PLAYER_GET_HANDLE;
	}
	
	private static Field ENTITY_PLAYER_LOCALE;
	public static Field getEntityPlayer_locale(){
		if(ENTITY_PLAYER_LOCALE == null){
			try {
				ENTITY_PLAYER_LOCALE = getField(PackageType.MINECRAFT_SERVER.getClass("EntityPlayer"), true, "locale");
			} catch (NoSuchFieldException | SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return ENTITY_PLAYER_LOCALE;
	}
}
