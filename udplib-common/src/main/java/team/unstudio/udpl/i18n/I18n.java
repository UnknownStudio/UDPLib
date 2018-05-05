package team.unstudio.udpl.i18n;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Player;
import team.unstudio.udpl.util.PlayerUtils;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * A Internationalization (I18n) pack.
 *
 * @author mouse
 * @since 1.1.1
 */
public interface I18n {
    /**
     * translates a message key into an internationalized String.
     *
     * @param locale the locale for which internationalized message resource is desired
     * @return message translated
     */
	String localize(Locale locale, String key);

    /**
     * translates a message key into an internationalized String with {@link I18n#getDefaultLocale()}.
     */
	default String localize(String key){
		return localize(getDefaultLocale(), key);
	}

    /**
     * translates a message key into an internationalized String.
     *
     * @param locale the locale‘s name for which internationalized message resource is desired
     * @return message translated
     */
	default String localize(String locale, String key){
		return localize(Locale.forLanguageTag(locale), key);
	}
	
	default String localize(Player player, String key){
		return localize(PlayerUtils.getLanguageLocale(player), key);
	}

	/**
	 * Using {@link String#format(String, Object...)} to handle translated message,
	 * if the parameter is a String, it will also be localized.
	 * e.g.
	 * 	i18n message:
	 * 		"text" -> "hello %s"
	 * 		"key" -> "world"
	 *
	 * 	format("text", "key") => "hello world"
	 *
     * @param locale the locale for which internationalized message resource is desired
	 * @param key the lang key
	 * @param args Arguments referenced by the format specifiers in the format
     *         string.  If there are more arguments than format specifiers, the
     *         extra arguments are ignored.  The number of arguments is
     *         variable and may be zero.  The maximum number of arguments is
     *         limited by the maximum dimension of a Java array as defined by
     *         <cite>The Java&trade; Virtual Machine Specification</cite>.
     *         The behaviour on a
     *         {@code null} argument depends on the <a
     *         href="../util/Formatter.html#syntax">conversion</a>.
	 * @return message translated, the key may be be returned if it couldn't be localized
	 */
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

    /**
     * Referencing {@link I18n#format(Locale, String, Object...)} with {@link I18n#getDefaultLocale()}
     */
	default String format(String key, Object... args){
		return format(getDefaultLocale(), key, args);
	}

    /**
     * Referencing {@link I18n#format(Locale, String, Object...)}
     */
	default String format(String locale, String key, Object... args){
		return format(Locale.forLanguageTag(locale), key, args);
	}

    /**
     * Referencing {@link I18n#format(Locale, String, Object...)} with Player's Locale (see {@link PlayerUtils#getLanguageLocale(Player)})
     */
	default String format(Player player, String key, Object... args){
		return format(PlayerUtils.getLanguageLocale(player), key, args);
	}

    /**
     * Gets the current value of the default locale for this instance
     * of the Java Virtual Machine.
     *
     * @return the default locale for this instance of the Java Virtual Machine
     */
	default Locale getDefaultLocale(){
		return Locale.getDefault();
	}
	
	default void setDefaultLocale(Locale locale){ throw new NotImplementedException("this i18n implementation haven't implement the custom default locale"); }

    /**
     * Referencing {@link I18n#setDefaultLocale(Locale)} with {@link Locale#forLanguageTag(String)}
     */
	default void setDefaultLocale(String locale){
		setDefaultLocale(Locale.forLanguageTag(locale));
	}
	
	@Nullable
	I18n getParent();
	
	void setParent(I18n parent);
}
