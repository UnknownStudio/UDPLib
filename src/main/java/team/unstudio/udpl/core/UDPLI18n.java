package team.unstudio.udpl.core;

import team.unstudio.udpl.i18n.ClassLoaderI18n;

import java.util.Locale;

/**
 * UDPLib本体所需的国际化配置
 * @author LasmGratel
 */
public interface UDPLI18n {
    ClassLoaderI18n I18N = new ClassLoaderI18n(UDPLib.class.getClassLoader(), "lang");

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
}
