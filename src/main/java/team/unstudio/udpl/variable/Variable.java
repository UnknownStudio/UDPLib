package team.unstudio.udpl.variable;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface Variable {

	String getName();

	String getValue(Player player);
	
	Plugin getPlugin();
}
