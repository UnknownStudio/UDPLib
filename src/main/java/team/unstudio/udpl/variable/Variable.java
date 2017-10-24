package team.unstudio.udpl.variable;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public interface Variable {
	
	String getValue(Player player, String name);
	
	Plugin getPlugin();
	
	default boolean isRegistrable(){
		return true;
	}
	
	default void register(){
		VariableHelper.registerVariable(this);
	}
}
