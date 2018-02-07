package team.unstudio.udpl.command.anno;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.Method;
import java.util.List;

import org.bukkit.command.CommandSender;

public class TabCompleteWrapper {
	
	private final CommandNode node;
	private final AnnoCommandManager manager;
	
	private final Object object;
	private final Method method;
	
	private final String permission;
	private final Class<? extends CommandSender>[] senders;
	private final boolean allowOp;
	
	private TabCompleteExecutor executor;
	
	public TabCompleteWrapper(CommandNode node, AnnoCommandManager manager, Object object, Method method, TabComplete tabComplete) {
		this.node = requireNonNull(node);
		this.manager = requireNonNull(manager);
		
		this.object = requireNonNull(object);
		this.method = requireNonNull(method);
		
		requireNonNull(tabComplete);
		permission = tabComplete.permission();
		senders = tabComplete.senders();
		allowOp = tabComplete.allowOp();
	}

	public CommandNode getNode() {
		return node;
	}

	public AnnoCommandManager getManager() {
		return manager;
	}
	
	public boolean checkPermission(CommandSender sender) {
		if(permission == null || permission.isEmpty()) 
			return true;
		
		if(allowOp && sender.isOp())
			return true;
		
		return sender.hasPermission(permission);
	}

	public boolean checkSender(CommandSender sender) {
		Class<?> senderClazz = sender.getClass();
		for (int i = 0; i < senders.length; i++)
			if (senders[i].isAssignableFrom(senderClazz))
				return true;
		
		return false;
	}
	
	public List<String> invoke(CommandSender sender, String args[]) {
		return executor.invoke(sender, args);
	}
	
	private interface TabCompleteExecutor {
		
		List<String> invoke(CommandSender sender, String args[]);
	}

}
