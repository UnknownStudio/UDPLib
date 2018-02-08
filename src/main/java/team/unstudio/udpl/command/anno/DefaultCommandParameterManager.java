package team.unstudio.udpl.command.anno;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

public class DefaultCommandParameterManager implements CommandParameterManager{

	protected final Map<Class<?>, CommandParameterHandler> handlers = new HashMap<>();

	public DefaultCommandParameterManager() {
		addHandler(new CommandParameterHandler.BooleanHandler());
		addHandler(new CommandParameterHandler.NumberHandler());
		addHandler(new CommandParameterHandler.PlayerHandler());
		addHandler(new CommandParameterHandler.OfflinePlayerHandler());
	}
	
	public DefaultCommandParameterManager(CommandParameterHandler... handlers) {
		this();
		addHandler(handlers);
	}
	
	public DefaultCommandParameterManager(Collection<CommandParameterHandler> handlers) {
		this();
		addHandler(handlers);
	}
	
	public void addHandler(CommandParameterHandler handler) {
		handlers.put(handler.getType(), handler);
	}
	
	public void addHandler(CommandParameterHandler... handlers) {
		for(CommandParameterHandler handler : handlers)
			addHandler(handler);
	}
	
	public void addHandler(Collection<CommandParameterHandler> handlers) {
		for(CommandParameterHandler handler : handlers)
			addHandler(handler);
	}

	@Override
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
		else if (handlers.containsKey(clazz))
			return handlers.get(clazz).transform(value);
		else
			throw new IllegalArgumentException(
					String.format("Can't transform parameter. Class: %1$s. Value: \"%2$s\".", clazz.getName(), value));
	}

	@Override
	public List<String> tabComplete(Class<?> clazz, String value) {
		return handlers.containsKey(clazz)
				? handlers.get(clazz).tabComplete(Strings.nullToEmpty(value))
				: Collections.emptyList();
	}
}
