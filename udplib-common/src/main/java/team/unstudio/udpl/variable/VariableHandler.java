package team.unstudio.udpl.variable;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface VariableHandler {

	String getName();

	String get(Player player, String key);

	Plugin getPlugin();

	default boolean isRegistrable(){
		return true;
	}

	default void register(){
		if(!isRegistrable())
			return;
		VariableHelper.register(this);
	}
}
