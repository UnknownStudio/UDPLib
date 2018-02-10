package team.unstudio.udpl.command;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import javax.annotation.Nonnull;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;

public interface CommandHelper {
	
	AtomicReference<CommandMap> commandMap = new AtomicReference<>();
	AtomicReference<Constructor<PluginCommand>> pluginCommandConstructor = new AtomicReference<>();

	/**
	 * 不安全的指令注册
	 * @param name 指令名
	 * @param plugin 插件主类实例
	 */
	static Optional<PluginCommand> unsafeRegisterCommand(@Nonnull String name, @Nonnull Plugin plugin){
		Validate.notEmpty(name);
		Validate.notNull(plugin);
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
			Bukkit.getLogger().log(Level.WARNING, e.getMessage(), e);
		}
		return Optional.empty();
	}
}
