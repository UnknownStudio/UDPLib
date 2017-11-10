package team.unstudio.udpl.variable;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.collect.Maps;

public class SimpleVariableHandler implements VariableHandler{
	
	private final String name;
	private final Plugin plugin;
	private final Map<String,Function<Player, String>> variableHandlers = Maps.newHashMap();

	public SimpleVariableHandler(@Nonnull String name, @Nonnull Plugin plugin) {
		Validate.notEmpty(name);
		Validate.notNull(plugin);
		this.name = name;
		this.plugin = plugin;
	}
	
	public SimpleVariableHandler(@Nonnull String name, @Nonnull Plugin plugin, Map<String,Function<Player, String>> variableHandlers) {
		Validate.notEmpty(name);
		Validate.notNull(plugin);
		this.name = name;
		this.plugin = plugin;
		this.variableHandlers.putAll(variableHandlers);
	}
	
	public Map<String, Function<Player, String>> getVariableHandlers() {
		return variableHandlers;
	}
	
	public SimpleVariableHandler addVariableHandler(String key, Function<Player, String> handler){
		variableHandlers.put(key, handler);
		return this;
	}
	
	@Override
	public Plugin getPlugin() {
		return plugin;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String get(Player player, String key) {
		if(!variableHandlers.containsKey(key))
			return null;
		
		return variableHandlers.get(key).apply(player);
	}
}
