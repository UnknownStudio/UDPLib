package team.unstudio.udpc.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 指令处理者
 */
public abstract class CommandHandler {

	private String[] sub;
	private String permission;
	@SuppressWarnings("unchecked")
	private Class<? extends CommandSender> senders[] = new Class[]{CommandSender.class};
	private Class<?>[] parameterTypes = new Class[0];
	private String usage = "";
	private String description = "";
	
	public String[] getSub() {
		return sub;
	}

	public CommandHandler setSub(String ...sub) {
		this.sub = sub;
		return this;
	}
	
	public String getPermission() {
		return permission;
	}

	public CommandHandler setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public Class<? extends CommandSender>[] getSenders() {
		return senders;
	}

	@SuppressWarnings("unchecked")
	public CommandHandler setSenders(Class<? extends CommandSender> ...senders) {
		this.senders = senders;
		return this;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public CommandHandler setParameterTypes(Class<?> ...parameterTypes) {
		this.parameterTypes = parameterTypes;
		return this;
	}

	public String getUsage() {
		return usage;
	}

	public CommandHandler setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CommandHandler setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public abstract boolean onCommand(CommandSender sender,Object[] args);
	
	public List<String> onTabComplete(String[] args){
		List<String> list = new ArrayList<>();
		for(Player player:Bukkit.getOnlinePlayers())if(player.getName().startsWith(args[0]))list.add(player.getName());
		return list;
	}
}
