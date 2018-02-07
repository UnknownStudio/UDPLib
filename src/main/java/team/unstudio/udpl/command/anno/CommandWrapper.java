package team.unstudio.udpl.command.anno;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unstudio.udpl.core.command.PluginManager;
import team.unstudio.udpl.core.command.UDPLCommand;
import team.unstudio.udpl.core.test.TestCommand;

import org.bukkit.command.Command;

public class CommandWrapper {
	private final CommandNode node;
	private final AnnoCommandManager manager;
	
	private final Object object;
	private final Method method;
	
	private final String permission;
	private final Class<? extends CommandSender>[] senders;
	private final boolean allowOp;
	private final boolean exactParameterMatching;
	
	private final String usage;
	private final String description;
	
	private boolean hasStringArray;
	
	private RequiredWrapper[] requireds;
	private OptionalWrapper[] optionals;
	
	private CommandExecutor executor;
	
	public CommandWrapper(CommandNode node, AnnoCommandManager manager, Object object, Method method, team.unstudio.udpl.command.anno.Command command) {
		this.node = requireNonNull(node);
		this.manager = requireNonNull(manager);
		
		this.object = requireNonNull(object);
		this.method = requireNonNull(method);
		
		requireNonNull(command);
		permission = command.permission();
		senders = command.senders();
		allowOp = command.allowOp();
		exactParameterMatching = command.exactParameterMatching();
		
		usage = command.usage();
		description = command.description();
	}

	public CommandNode getNode() {
		return node;
	}

	public AnnoCommandManager getManager() {
		return manager;
	}
	
	public String getUsage() {
		return usage;
	}

	public String getDescription() {
		return description;
	}
	
	public String getPermission() {
		return permission;
	}

	public boolean isExactParameterMatching() {
		return exactParameterMatching;
	}
	
	public boolean hasStringArray() {
		return hasStringArray;
	}
	
	public RequiredWrapper[] getRequireds() {
		return requireds;
	}
	
	public OptionalWrapper[] getOptionals() {
		return optionals;
	}

	public boolean checkPermission(CommandSender sender) {
		if(permission == null || permission.isEmpty()) 
			return true;
		
		if(allowOp && sender.isOp())
			return true;
		
		return sender.hasPermission(permission);
	}

	public boolean checkSender(CommandSender sender) {
		return checkSender(sender.getClass());
	}
	
	public boolean checkSender(Class<? extends CommandSender> sender) {
		for (int i = 0; i < senders.length; i++)
			if (senders[i].isAssignableFrom(sender))
				return true;
		
		return false;
	}
	
	public void invoke(CommandSender sender, Object args[]) {
		executor.invoke(sender, args);
	}
	
	public static class RequiredWrapper {
		
		private final Class<?> type;
		private final String name;
		private final String usage;
		private final String[] complete;
				
		public RequiredWrapper(Class<?> type, Required required) {
			this.type = requireNonNull(type);
			requireNonNull(required);
			this.name = required.name();
			this.usage = required.usage();
			this.complete = required.complete();
		}

		public Class<?> getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getUsage() {
			return usage;
		}

		public String[] getComplete() {
			return complete;
		}
	}
	
	public static class OptionalWrapper {
		
		private final Class<?> type;
		private final String name;
		private final String usage;
		private final String[] complete;
		private final String defaultValue;
				
		public OptionalWrapper(Class<?> type, Optional optional) {
			this.type = requireNonNull(type);
			requireNonNull(optional);
			this.name = optional.name();
			this.usage = optional.usage();
			this.complete = optional.complete();
			this.defaultValue = optional.value();
		}

		public Class<?> getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getUsage() {
			return usage;
		}

		public String[] getComplete() {
			return complete;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
	}

	public static interface CommandExecutor {
		
		void invoke(CommandSender sender, Object args[]);
	}
	
	private static class Dynamic implements CommandExecutor {

		TestCommand instance;
		
		@Override
		public void invoke(CommandSender sender, Object[] args) {
			onCommand(sender, args[0], args[1]);
		}
		
		public void onCommand(CommandSender sender, Object arg1, Object arg2) {
			
		}
	}
}
