package team.unstudio.udpl.variable;

import java.util.Map;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

public final class VariableHelper {

	private VariableHelper(){}
	
	private static final Map<String,Variable> REGISTED_VARIABLES = Maps.newHashMap();
	
	public static void registerVariable(@Nonnull Variable variable){
		Validate.notNull(variable);
		REGISTED_VARIABLES.put(variable.getName(), variable);
	}

	public static SimpleVariable registerVariable(@Nonnull Plugin plugin, @Nonnull String name, @Nonnull Function<Player, String> function){
		SimpleVariable variable = new SimpleVariable(plugin, name, function);
		registerVariable(variable);
		return variable;
	}
	
	public static void unregisterVariable(@Nonnull Variable variable){
		Validate.notNull(variable);
		REGISTED_VARIABLES.remove(variable.getName());
	}
	
	public static void unregisterVariable(@Nonnull String variable){
		Validate.notEmpty(variable);
		REGISTED_VARIABLES.remove(variable);
	}
	
	public static String apply(Player player, String value){
		return apply(player, value, '%', '%');
	}
	
	public static String apply(Player player, String value, char variableStart, char variableEnd){
		StringBuilder result = new StringBuilder(value.length());
		StringBuilder variableName = new StringBuilder();
		boolean variableMatchesing = false;
		for(char c:value.toCharArray()){
			if(variableMatchesing){
				if(c == variableEnd){
					variableMatchesing = false;
					String variable = getValue(player, variableName.toString());
					if(Strings.isNullOrEmpty(variable))
						result.append(variableStart).append(variableName.toString()).append(variableEnd);
					else
						result.append(variable);
					variableName.delete(0, variableName.length());
				}else
					variableName.append(c);
			}else{
				if(c == variableStart)
					variableMatchesing = true;
				else
					result.append(c);
			}
		}
		return result.toString();
	}
	
	private static String getValue(Player player,String name){
		if(REGISTED_VARIABLES.containsKey(name))
			return REGISTED_VARIABLES.get(name).getValue(player);
		else
			return null;
	}
}
