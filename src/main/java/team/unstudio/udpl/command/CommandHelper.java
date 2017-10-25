package team.unstudio.udpl.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import team.unstudio.udpl.core.UDPLib;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public interface CommandHelper {
	boolean DEBUG = UDPLib.isDebug();
	
	AtomicReference<CommandMap> commandMap = new AtomicReference<>();
	AtomicReference<Constructor<PluginCommand>> pluginCommandConstructor = new AtomicReference<>();

	static Optional<PluginCommand> unsafeRegisterCommand(String name, Plugin plugin){
		try {
			if(commandMap.get() == null){
				//noinspection JavaReflectionMemberAccess
				Method getCommandMap = Bukkit.getServer().getClass().getDeclaredMethod("getCommandMap");
				commandMap.set((CommandMap) getCommandMap.invoke(Bukkit.getServer()));
			}
			if(pluginCommandConstructor.get() == null){
				pluginCommandConstructor.set(PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class));
				pluginCommandConstructor.get().setAccessible(true);
			}
			PluginCommand command = pluginCommandConstructor.get().newInstance(name,plugin);
			commandMap.get().register(plugin.getName(), command);
			return Optional.of(command);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
			UDPLib.debug(e);
		}
		return Optional.empty();
	}
}
