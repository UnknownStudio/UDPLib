package team.unstudio.udpl.command.anno;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import team.unstudio.udpl.util.ServerUtils;

public interface CommandParameterHandler {

	/**
	 * 参数转换
	 * 
	 * @param value
	 * @return
	 */
	Object transform(String value);

	/**
	 * 参数自动补全
	 * 
	 * @param value
	 * @return
	 */
	default List<String> tabComplete(String value) {
		return Collections.emptyList();
	}

	public static class BooleanHandler implements CommandParameterHandler {

		@Override
		public Object transform(String value) {
			return Boolean.valueOf(value);
		}

		private static final List<String> BOOLEANS = ImmutableList.of("true", "false");

		@Override
		public List<String> tabComplete(String value) {
			String prefix = Strings.nullToEmpty(value).toLowerCase();
			return BOOLEANS.stream().filter(str -> str.startsWith(prefix)).collect(Collectors.toList());
		}

	}

	public static class PlayerHandler implements CommandParameterHandler {

		@Override
		public Object transform(String value) {
			return Bukkit.getPlayerExact(value);
		}

		@Override
		public List<String> tabComplete(String value) {
			String prefix = Strings.nullToEmpty(value);
			return Arrays.asList(ServerUtils.getOnlinePlayerNamesWithFilter(name -> name.startsWith(prefix)));
		}
	}

	public static class OfflinePlayerHandler implements CommandParameterHandler {

		@SuppressWarnings("deprecation")
		@Override
		public Object transform(String value) {
			return Bukkit.getOfflinePlayer(value);
		}
	}

	public static class NumberHandler implements CommandParameterHandler {
		
		private static final Pattern DOUBLE_PATTERN = getDoublePattern();

		@Override
		public Object transform(String value) {
			return DOUBLE_PATTERN.matcher(value).matches() ? Double.valueOf(value) : Long.valueOf(value);
		}

		private static Pattern getDoublePattern() {
			final String Digits = "(\\p{Digit}+)";
			final String HexDigits = "(\\p{XDigit}+)";
			final String Exp = "[eE][+-]?" + Digits;
			final String fpRegex = ("[\\x00-\\x20]*" + "[+-]?(" + "NaN|" + "Infinity|" + "(((" + Digits + "(\\.)?("
					+ Digits + "?)(" + Exp + ")?)|" + "(\\.(" + Digits + ")(" + Exp + ")?)|" + "((" + "(0[xX]"
					+ HexDigits + "(\\.)?)|" + "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" + ")[pP][+-]?" + Digits
					+ "))" + "[fFdD]?))" + "[\\x00-\\x20]*");
			return Pattern.compile(fpRegex);
		}
	}
	
	static <E extends Enum<E>> CommandParameterHandler createEnumHandler(Class<E> clazz) {
		return new CommandParameterHandler() {
			
			@Override
			public Object transform(String value) {
				return Enum.valueOf(clazz, value);
			}
			
			private final List<String> elements = Arrays.stream(clazz.getEnumConstants()).map(e->e.name()).collect(Collectors.toList());
			
			@Override
			public List<String> tabComplete(String value) {
				String prefix = Strings.nullToEmpty(value);
				return elements.stream().filter(e->e.startsWith(prefix)).collect(Collectors.toList());
			}
		};
	}
}
