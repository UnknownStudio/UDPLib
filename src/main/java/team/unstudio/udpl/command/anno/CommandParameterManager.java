package team.unstudio.udpl.command.anno;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.google.common.base.Strings;

public class CommandParameterManager {

	protected Map<Class<?>, CommandParameterHandler> parameterHandlers;

	public CommandParameterManager() {
		this(null);
	}

	public CommandParameterManager(Map<Class<?>, CommandParameterHandler> handlers) {
		init(handlers);
	}

	protected Map<Class<?>, CommandParameterHandler> init(Map<Class<?>, CommandParameterHandler> handlers) {
		Map<Class<?>, CommandParameterHandler> map = new HashMap<>();

		map.put(boolean.class, new CommandParameterHandler.BooleanHandler());
		map.put(Player.class, new CommandParameterHandler.PlayerHandler());
		map.put(OfflinePlayer.class, new CommandParameterHandler.OfflinePlayerHandler());
		map.put(Number.class, new CommandParameterHandler.NumberHandler());

		if (parameterHandlers != null)
			map.putAll(parameterHandlers);

		return map;
	}

	public Object transform(Class<?> clazz, String value) {
		if (value == null)
			return null;
		else if (clazz.equals(String.class))
			return value;
		else if (clazz.equals(int.class))
			return Integer.parseInt(value);
		else if (clazz.equals(float.class))
			return Float.parseFloat(value);
		else if (clazz.equals(double.class))
			return Double.parseDouble(value);
		else if (clazz.equals(long.class))
			return Long.parseLong(value);
		else if (clazz.equals(byte.class))
			return Byte.parseByte(value);
		else if (clazz.equals(short.class))
			return Short.parseShort(value);
		else if (parameterHandlers.containsKey(clazz))
			return parameterHandlers.get(clazz).transform(value);
		else
			throw new IllegalArgumentException(
					String.format("Can't transform parameter. Class: %1$s. Value: \"%2$s\".", clazz.getName(), value));
	}

	public List<String> tabCompleteParameter(Class<?> clazz, String value) {
		return parameterHandlers.containsKey(clazz)
				? parameterHandlers.get(clazz).tabComplete(Strings.nullToEmpty(value))
				: Collections.emptyList();
	}
}
