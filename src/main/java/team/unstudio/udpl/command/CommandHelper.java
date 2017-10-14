package team.unstudio.udpl.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

public final class CommandHelper {
	
	private static SimpleCommandMap commandMap;

	@Nullable
	public static Optional<PluginCommand> unsafeRegisterCommand(String name,Plugin plugin){
		try {
			if(commandMap == null){
				Method getCommandMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap");
				commandMap = (SimpleCommandMap) getCommandMap.invoke(Bukkit.getServer());
			}
			PluginCommand command = PluginCommand.class.getConstructor(String.class,Plugin.class).newInstance(name,plugin);
			commandMap.register(plugin.getName(), command);
			return Optional.of(command);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
		}
		return Optional.empty();
	}
}
