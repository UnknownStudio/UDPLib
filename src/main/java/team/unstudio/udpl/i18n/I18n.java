package team.unstudio.udpl.i18n;

import java.util.Locale;

import org.bukkit.entity.Player;

import team.unstudio.udpl.util.PlayerUtils;

public interface I18n {
	Locale DEFAULT_LOCALE = Locale.US;
	
	String format(Locale locale, String key);
	
	default String format(String key){
		return format(Locale.getDefault(), key);
	}
	
	default String format(String locale, String key){
		return format(Locale.forLanguageTag(locale), key);
	}
	
	default String format(Player player, String key){
		return format(PlayerUtils.getLanguageLocale(player), key);
	}
	
	default String format(Locale locale, String key, Object... args){
		Object[] localizedArgs = new Object[args.length];
		for (int i = 0, size = args.length; i < size; i++) {
			Object arg = args[i];
			if(arg instanceof String)
				localizedArgs[i] = format(locale, (String) arg);
			else
				localizedArgs[i] = arg;
		}
		return String.format(format(locale, key), localizedArgs);
	}
	
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
