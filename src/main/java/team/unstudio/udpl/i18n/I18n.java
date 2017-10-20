package team.unstudio.udpl.i18n;

import java.util.Locale;

import org.bukkit.entity.Player;

import team.unstudio.udpl.util.PlayerUtils;

public interface I18n {
	
	String format(Locale locale, String key, Object... args);
	
	default String format(String key, Object... args){
		return format(Locale.getDefault(), key, args);
	}
	
	default String format(String locale, String key, Object... args){
		return format(Locale.forLanguageTag(locale), key, args);
	}
	
	default String format(Player player, String key, Object... args){
		return format(PlayerUtils.getLanguageLocale(player), key, args);
	}
	
	default void sendMessage(Player player, String locale, String key, Object... args){
		player.sendMessage(format(locale, key, args));
	}
	
	default void sendMessage(Player player, Locale locale, String key, Object... args){
		player.sendMessage(format(locale, key, args));
	}
	
	default void sendMessage(Player player, String key, Object... args){
		player.sendMessage(format(key, args));
	}
	
	default void sendLocalizedMessage(Player player, String key, Object... args){
		player.sendMessage(format(player, key, args));
	}
}
