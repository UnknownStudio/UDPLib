package team.unstudio.udpl.variable;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

public abstract class AbstractVariable implements Variable{

	private final Plugin plugin;
	
	public AbstractVariable(@Nonnull Plugin plugin) {
		Validate.notNull(plugin);
		this.plugin = plugin;
	}
	
	@Override
	public Plugin getPlugin() {
		return plugin;
	}
}
