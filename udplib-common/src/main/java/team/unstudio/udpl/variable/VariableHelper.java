package team.unstudio.udpl.variable;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.Set;

public interface VariableHelper {
	Set<VariableHandler> REGISTERED_VARIABLE_HANDLERS = Sets.newLinkedHashSet();

	static void register(@Nonnull VariableHandler handler){
		Validate.notNull(handler);
		REGISTERED_VARIABLE_HANDLERS.add(handler);
	}

	static void unregister(@Nonnull VariableHandler handler){
		Validate.notNull(handler);
		REGISTERED_VARIABLE_HANDLERS.remove(handler);
	}

	static void unregister(@Nonnull String name){
		Validate.notEmpty(name);
		REGISTERED_VARIABLE_HANDLERS.removeIf(handler->name.equals(handler.getName()));
	}

	static void unregister(@Nonnull Plugin plugin){
		Validate.notNull(plugin);
		REGISTERED_VARIABLE_HANDLERS.removeIf(handler->plugin.equals(handler.getPlugin()));
	}

	static String apply(Player player, String value){
		return apply(player, value, '%', '%');
	}

	static String apply(Player player, String value, char handlerStart, char handlerEnd){
		StringBuilder result = new StringBuilder(value.length());
		StringBuilder handlerName = new StringBuilder();
		boolean handlerMatching = false;
		for(char c:value.toCharArray()){
			if(handlerMatching){
				if(c == handlerEnd){
					handlerMatching = false;
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
					handlerMatching = true;
				else
					result.append(c);
			}
		}
		return result.toString();
	}

	static String getValue(Player player, String name){
		for(VariableHandler handler : REGISTERED_VARIABLE_HANDLERS){
			try{
				String value = handler.get(player, name);
				if(value != null) return value;
			}catch(Exception ignored){}
		}
		return null;
	}
}
