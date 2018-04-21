package team.unstudio.udpl.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListeningWhitelist;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.events.PacketListener;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.util.reflect.NMSReflectionUtils;

import javax.annotation.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.Map;

public interface PlayerUtils {
	
	ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
	Map<Player, Locale> PLAYER_LANGUAGE_CACHE = Maps.newConcurrentMap();
	
	@Init
	static void initPlayerUtils(){
		ProtocolLibUtils.listenOnPacketReceiving(event -> {
			Player player = event.getPlayer();
			PacketContainer container = event.getPacket();
			String languageTag = container.getStrings().read(0);
			String normalizedLanguageTag = normalizeLanguageTag(languageTag);
			Locale locale = Locale.forLanguageTag(normalizedLanguageTag);
			if(locale == Locale.ROOT)
				return;
			PLAYER_LANGUAGE_CACHE.put(player, locale);
		}, PacketType.Play.Client.SETTINGS);
	}
	
	Locale DEFAULT_LANGUAGE = Locale.US;
	static String getLanguage(Player player){
		return getLanguageLocale(player).toLanguageTag();
	}
	
	static Locale getLanguageLocale(Player player){
		if(!PLAYER_LANGUAGE_CACHE.containsKey(player)){
			try {
				PLAYER_LANGUAGE_CACHE.put(player, 
						Locale.forLanguageTag(normalizeLanguageTag((String) NMSReflectionUtils.getLocaleNMS().get(NMSReflectionUtils.getHandleNMS().invoke(player)))));
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
				UDPLib.debug(e);
				PLAYER_LANGUAGE_CACHE.put(player, DEFAULT_LANGUAGE);
			}
		}
		return PLAYER_LANGUAGE_CACHE.get(player);
	}
	
	static String normalizeLanguageTag(String languageTag){
		languageTag = languageTag.replaceAll("_", "-");
		int first = languageTag.indexOf("-"), second = languageTag.indexOf("-", first+1);
		if(first == -1)
			return languageTag;
		else if(second == -1){
			return languageTag.substring(0, first+1) + languageTag.substring(first+1).toUpperCase();
		}else{
			return languageTag.substring(0, first+1) + languageTag.substring(first+1,second).toUpperCase() + languageTag.substring(second);
		}
	}
	
	@Nullable
	static Block getTargetBlock(Player player){
		return player.getTargetBlock(Sets.newHashSet(Material.AIR), 100);
	}
	
	//exp helper
	/**
	 * 设置总经验
	 */
	static void setTotalExperience(Player player, int exp) {
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
	static int getExpAtLevel(Player player) {
		return getExpAtLevel(player.getLevel());
	}

	/**
	 * 获取到该等级的经验
	 */
	static int getExpAtLevel(int level) {
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
	static int getExpToLevel(int level) {
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
	static int getTotalExperience(Player player) {
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
	static int getExpUntilNextLevel(Player player) {
		int exp = Math.round(getExpAtLevel(player) * player.getExp());
		int nextLevel = player.getLevel();
		return getExpAtLevel(nextLevel) - exp;
	}
}
