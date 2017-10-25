package team.unstudio.udpl.variable;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SimpleVariable implements Variable{
	
	private final String name;
	private final Function<Player, String> function;
	private final Plugin plugin;

	public SimpleVariable(@Nonnull Plugin plugin, @Nonnull String name, @Nonnull Function<Player, String> function) {
		Validate.notNull(plugin);
		Validate.notEmpty(name);
		Validate.notNull(function);
		this.plugin = plugin;
		this.name = name;
		this.function = function;
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
	public String getValue(Player player) {
		return function.apply(player);
	}
}
