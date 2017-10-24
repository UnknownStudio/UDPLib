package team.unstudio.udpl.variable;

import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class SingleVariable extends AbstractVariable{
	
	private final String name;
	private final Function<Player, String> function;

	public SingleVariable(@Nonnull Plugin plugin, @Nonnull String name, @Nonnull Function<Player, String> function) {
		super(plugin);
		Validate.notEmpty(name);
		Validate.notNull(function);
		this.name = name;
		this.function = function;
	}

	public String getName() {
		return name;
	}

	@Override
	public String getValue(Player player, String name) {
		if(!this.name.equals(name))
			return null;
		return function.apply(player);
	}
}
