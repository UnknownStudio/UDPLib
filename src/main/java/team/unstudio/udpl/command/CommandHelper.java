package team.unstudio.udpl.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.Plugin;

public interface CommandHelper {

	@Nullable
	public static Command unsafeRegisterCommand(String name,Plugin plugin){
		try {
			Method getCommandMap = Bukkit.getServer().getClass().getMethod("getCommandMap");
			SimpleCommandMap commandMap = (SimpleCommandMap) getCommandMap.invoke(Bukkit.getServer());
			PluginCommand command = PluginCommand.class.getConstructor(String.class,Plugin.class).newInstance(name,plugin);
			commandMap.register(plugin.getName(), command);
			return command;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | InstantiationException e) {
		}
		return null;
	}
}
