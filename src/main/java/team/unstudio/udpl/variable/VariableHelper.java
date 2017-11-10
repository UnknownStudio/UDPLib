package team.unstudio.udpl.variable;

import java.util.Set;
import javax.annotation.Nonnull;

import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;

public final class VariableHelper {

	private VariableHelper(){}
	
	private static final Set<VariableHandler> REGISTED_VARIABLE_HANDLERS = Sets.newLinkedHashSet();
	
	public static void registerVariable(@Nonnull VariableHandler handler){
		Validate.notNull(handler);
		REGISTED_VARIABLE_HANDLERS.add(handler);
	}
	
	public static void unregisterVariable(@Nonnull VariableHandler handler){
		Validate.notNull(handler);
		REGISTED_VARIABLE_HANDLERS.remove(handler.getName());
	}
	
	public static void unregisterVariable(@Nonnull String name){
		Validate.notEmpty(name);
		REGISTED_VARIABLE_HANDLERS.removeIf(handler->name.equals(handler.getName()));
	}
	
	public static void unregisterVariable(@Nonnull Plugin plugin){
		Validate.notNull(plugin);
		REGISTED_VARIABLE_HANDLERS.removeIf(handler->plugin.equals(handler.getPlugin()));
	}
	
	public static String apply(Player player, String value){
		return apply(player, value, '%', '%');
	}
	
	public static String apply(Player player, String value, char handlerStart, char handlerEnd){
		StringBuilder result = new StringBuilder(value.length());
		StringBuilder handlerName = new StringBuilder();
		boolean handlerMatchesing = false;
		for(char c:value.toCharArray()){
			if(handlerMatchesing){
				if(c == handlerEnd){
					handlerMatchesing = false;
					String handler = getValue(player, handlerName.toString());
					if(Strings.isNullOrEmpty(handler))
						result.append(handlerStart).append(handlerName.toString()).append(handlerEnd);
					else
						result.append(handler);
					handlerName.delete(0, handlerName.length());
				}else
					handlerName.append(c);
			}else{
				if(c == handlerStart)
					handlerMatchesing = true;
				else
					result.append(c);
			}
		}
		return result.toString();
	}
	
	private static String getValue(Player player, String name){
		for(VariableHandler handler : REGISTED_VARIABLE_HANDLERS){
			String value = handler.get(player, name);
			if(value == null)
				continue;
			
			return value;
		}
		return null;
	}
}
