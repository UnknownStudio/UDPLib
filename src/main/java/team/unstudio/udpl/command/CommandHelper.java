package team.unstudio.udpl.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import team.unstudio.udpl.core.UDPLib;

public final class CommandHelper {
	
	private static final boolean DEBUG = UDPLib.isDebug();
	
	private static CommandMap commandMap;

	public static Optional<PluginCommand> unsafeRegisterCommand(String name,Plugin plugin){
		try {
			if(commandMap == null){
				Method getCommandMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap");
				commandMap = (CommandMap) getCommandMap.invoke(Bukkit.getServer());
			}
			PluginCommand command = PluginCommand.class.getDeclaredConstructor(String.class,Plugin.class).newInstance(name,plugin);
			commandMap.register(plugin.getName(), command);
			return Optional.of(command);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			if(DEBUG)
				e.printStackTrace();
		}
		return Optional.empty();
	}
}
