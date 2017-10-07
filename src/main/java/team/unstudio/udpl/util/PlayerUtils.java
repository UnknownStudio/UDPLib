package team.unstudio.udpl.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import org.bukkit.entity.Player;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.ReflectionUtils;
import team.unstudio.udpl.nms.ReflectionUtils.PackageType;

public final class PlayerUtils {
	
	private PlayerUtils(){}
	
	private static final boolean debug = UDPLib.isDebug();
	
	public static final String DEFAULT_LANGUAGE = "en_US";
	public static String getLanguage(Player player){
		try {
			Method getHandle = ReflectionUtils.getMethod("CraftPlayer", PackageType.CRAFTBUKKIT_ENTITY, "getHandle");
			Field locale = ReflectionUtils.getField(PackageType.MINECRAFT_SERVER.getClass("EntityPlayer"), true, "locale");
			return (String) locale.get(getHandle.invoke(player));
		} catch (NoSuchMethodException | ClassNotFoundException | NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			if(debug)
				e.printStackTrace();
		}
		return DEFAULT_LANGUAGE;
	}
	public static Locale getLanguageLocale(Player player){
		return Locale.forLanguageTag(getLanguage(player));
	}
	
	//exp helper
	/**
	 * 设置总经验
	 */
	public static void setTotalExperience(Player player, int exp) {
		if (exp < 0) {
			throw new IllegalArgumentException("Experience is negative!");
		}
		player.setExp(0.0F);
		player.setLevel(0);
		player.setTotalExperience(0);

		int amount = exp;
		while (amount > 0) {
			int expToLevel = getExpAtLevel(player);
			amount -= expToLevel;
			if (amount >= 0) {
				player.giveExp(expToLevel);
			} else {
				amount += expToLevel;
				player.giveExp(amount);
				amount = 0;
			}
		}
	}

	/**
	 * 获取到该等级的经验
	 */
	private static int getExpAtLevel(Player player) {
		return getExpAtLevel(player.getLevel());
	}

	/**
	 * 获取到该等级的经验
	 */
	public static int getExpAtLevel(int level) {
		if (level > 29) {
			return 62 + (level - 30) * 7;
		}
		if (level > 15) {
			return 17 + (level - 15) * 3;
		}
		return 17;
	}

	/**
	 * 获取升级到某等级所需经验
	 */
	public static int getExpToLevel(int level) {
		int currentLevel = 0;
		int exp = 0;

		while (currentLevel < level) {
			exp += getExpAtLevel(currentLevel);
			currentLevel++;
		}
		if (exp < 0) {
			exp = 2147483647;
		}
		return exp;
	}

	/**
	 * 获取总经验
	 */
	public static int getTotalExperience(Player player) {
		int exp = Math.round(getExpAtLevel(player) * player.getExp());
		int currentLevel = player.getLevel();

		while (currentLevel > 0) {
			currentLevel--;
			exp += getExpAtLevel(currentLevel);
		}
		if (exp < 0) {
			exp = 2147483647;
		}
		return exp;
	}

	/**
	 * 获取到下一等级的还缺少的经验
	 */
	public static int getExpUntilNextLevel(Player player) {
		int exp = Math.round(getExpAtLevel(player) * player.getExp());
		int nextLevel = player.getLevel();
		return getExpAtLevel(nextLevel) - exp;
	}
}
