package team.unstudio.udpl.core;

import org.bukkit.entity.Player;
import team.unstudio.udpl.i18n.YamlI18n;

import java.io.File;
import java.util.Locale;

/**
 * UDPLib本体所需的国际化配置
 * @author LasmGratel
 */
public interface UDPLI18n {
    YamlI18n I18N = YamlI18n.fromFile(new File(UDPLib.getInstance().getDataFolder(), "lang"));

    static void setLocale(Locale locale) {
        I18N.setDefaultLocale(locale);
    }

    static void setLocale(String locale) {
        I18N.setDefaultLocale(locale);
    }

    static String format(String key) {
        return I18N.localize(key);
    }

    static String format(String key, Object... args) {
        return I18N.format(key, args);
    }

    static String format(Locale locale, String key, Object... args) {
        return I18N.format(locale, key, args);
    }
    
    static String format(Player player, String key){
    	return I18N.localize(player, key);
    }
    
    static String format(Player player, String key, Object... args){
    	return I18N.format(player, key, args);
    }
}
