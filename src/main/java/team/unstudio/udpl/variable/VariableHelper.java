package team.unstudio.udpl.variable;

import java.util.Set;
import java.util.function.Function;

import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public final class VariableHelper {

	private VariableHelper(){}
	
	private static final Set<Variable> VARIABLES = Sets.newHashSet();
	
	public static void registerVariable(@Nonnull Variable variable){
		Validate.notNull(variable);
		if(!variable.isRegistrable())
			return;
		VARIABLES.add(variable);
	}

	public static SingleVariable registerSingleVariable(@Nonnull Plugin plugin, @Nonnull String name, @Nonnull Function<Player, String> function){
		SingleVariable variable = new SingleVariable(plugin, name, function);
		registerVariable(variable);
		return variable;
	}
	
	public static void unregisterVariable(@Nonnull Variable variable){
		Validate.notNull(variable);
		VARIABLES.remove(variable);
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
					result.append(getValue(player, variableName.toString()));
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
		for(Variable variable:VARIABLES){
			String result = variable.getValue(player, name);
			if(!Strings.isNullOrEmpty(result))
				return result;
		}
		return "";
	}
}
