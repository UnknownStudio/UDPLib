package team.unstudio.udpl.i18n;

import java.util.Locale;

import org.bukkit.entity.Player;

import team.unstudio.udpl.util.PlayerUtils;

public interface I18n {
	Locale DEFAULT_LOCALE = Locale.US;
	
	String localize(Locale locale, String key);
	
	default String localize(String key){
		return localize(getDefaultLocale(), key);
	}
	
	default String localize(String locale, String key){
		return localize(Locale.forLanguageTag(locale), key);
	}
	
	default String localize(Player player, String key){
		return localize(PlayerUtils.getLanguageLocale(player), key);
	}
	
	default String format(Locale locale, String key, Object... args){
		Object[] localizedArgs = new Object[args.length];
		for (int i = 0, size = args.length; i < size; i++) {
			Object arg = args[i];
			if(arg instanceof String)
				localizedArgs[i] = localize(locale, (String) arg);
			else
				localizedArgs[i] = arg;
		}
		return String.format(localize(locale, key), localizedArgs);
	}
	
	default String format(String key, Object... args){
		return format(getDefaultLocale(), key, args);
	}
	
	default String format(String locale, String key, Object... args){
		return format(Locale.forLanguageTag(locale), key, args);
	}
	
	default String format(Player player, String key, Object... args){
		return format(PlayerUtils.getLanguageLocale(player), key, args);
	}
	
	default Locale getDefaultLocale(){
		return Locale.getDefault();
	}
	
	default void setDefaultLocale(Locale locale){}
	
	default void setDefaultLocale(String locale){
		setDefaultLocale(Locale.forLanguageTag(locale));
	}
}
