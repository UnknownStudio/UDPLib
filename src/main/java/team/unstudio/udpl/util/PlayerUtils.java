package team.unstudio.udpl.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.util.ReflectionUtils.PackageType;

public final class PlayerUtils {
	
	private PlayerUtils(){}
	
	private static final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	private static WeakHashMap<Player, Locale> PLAYER_LANGUAGE_CACHE = new WeakHashMap<>();
	
	public static void initPlayerUtils(){
		protocolManager.addPacketListener(new PacketListener() {
			
			@Override
			public void onPacketSending(PacketEvent arg0) {}
			
			@Override
			public void onPacketReceiving(PacketEvent arg0) {
				Player player = arg0.getPlayer();
				PacketContainer container = arg0.getPacket();
				String locale = container.getStrings().read(0);
				PLAYER_LANGUAGE_CACHE.put(player, Locale.forLanguageTag(locale));
			}
			
			@Override
			public ListeningWhitelist getSendingWhitelist() {
				return ListeningWhitelist.EMPTY_WHITELIST;
			}
			
			@Override
			public ListeningWhitelist getReceivingWhitelist() {
				return ListeningWhitelist.newBuilder().lowest().types(PacketType.Play.Client.SETTINGS).build();
			}
			
			@Override
			public Plugin getPlugin() {
				return UDPLib.getInstance();
			}
		});
	}
	
	public static final Locale DEFAULT_LANGUAGE = Locale.US;
	public static String getLanguage(Player player){
		return getLanguageLocale(player).toLanguageTag();
	}
	
	public static Locale getLanguageLocale(Player player){
		if(!PLAYER_LANGUAGE_CACHE.containsKey(player)){
			try {
				PLAYER_LANGUAGE_CACHE.put(player, Locale.forLanguageTag((String) NMSReflectionUtils.getEntityPlayer_locale().get(NMSReflectionUtils.getCraftPlayer_getHandle().invoke(player))));
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				UDPLib.debug(e);
				PLAYER_LANGUAGE_CACHE.put(player, DEFAULT_LANGUAGE);
			}
		}
		return PLAYER_LANGUAGE_CACHE.get(player);
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
