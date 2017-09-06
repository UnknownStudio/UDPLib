package team.unstudio.udpl.command.tree;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unstudio.udpl.command.CommandResult;

public abstract class CommandNode {
	
	private String node;
	private final List<CommandNode> children = new ArrayList<>();
	private String permission;
	@SuppressWarnings("unchecked")
	private Class<? extends CommandSender> senders[] = new Class[]{CommandSender.class};
	private Class<?>[] parameterTypes = new Class[0];
	private String usage = "";
	private String description = "";

	public String getNode() {
		return node;
	}

	public CommandNode setNode(String node) {
		this.node = node;
		return this;
	}
	
	public String getPermission() {
		return permission;
	}

	public CommandNode setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public Class<? extends CommandSender>[] getSenders() {
		return senders;
	}

	@SuppressWarnings("unchecked")
	public CommandNode setSenders(Class<? extends CommandSender> ...senders) {
		this.senders = senders;
		return this;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public CommandNode setParameterTypes(Class<?> ...parameterTypes) {
		this.parameterTypes = parameterTypes;
		return this;
	}

	public String getUsage() {
		return usage;
	}

	public CommandNode setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public CommandNode setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public CommandNode addChildren(CommandNode sub){
		children.add(sub);
		return this;
	}
	
	public List<CommandNode> getChildren(){
		return children;
	}
	
	public CommandResult onCommand(CommandSender sender,String[] args){
		boolean flag = true;
		for (Class<? extends CommandSender> s : getSenders()) {
			if (s.isAssignableFrom(sender.getClass())) {
				flag = false;
				break;
			}
		}
		if (flag) return CommandResult.WrongSender;

		if (getPermission() != null && !sender.hasPermission(getPermission())) return CommandResult.NoPermission;

		if (getParameterTypes().length > args.length) return CommandResult.NoEnoughParameter;

		Object[] objs = new Object[args.length];
		
		if(!parseParameter(args,objs)) return CommandResult.ErrorParameter;
		
		if(!onCommand(sender, objs)) return CommandResult.Failure;
		else return CommandResult.Success;
	}
	
	public abstract boolean onCommand(CommandSender sender,Object[] args);
	
	public List<String> onTabComplete(String[] args){
		List<String> list = new ArrayList<>();
		for(Player player:Bukkit.getOnlinePlayers())if(player.getName().startsWith(args[0]))list.add(player.getName());
		return list;
	}
	private boolean parseParameter(String[] args,Object[] parameter) {
		for (int i = 0; i < args.length; i++) {
			try {
				String s = args[i];
				if (i < getParameterTypes().length) {
					Class<?> clazz = getParameterTypes()[i];
					if (clazz.equals(String.class))
						parameter[i] = s;
					else if (clazz.equals(int.class) || clazz.equals(Integer.class))
						parameter[i] = Integer.parseInt(s);
					else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
						parameter[i] = Boolean.parseBoolean(s);
					else if (clazz.equals(float.class) || clazz.equals(Float.class))
						parameter[i] = Float.parseFloat(s);
					else if (clazz.equals(double.class) || clazz.equals(Double.class))
						parameter[i] = Double.parseDouble(s);
					else if (clazz.equals(long.class) || clazz.equals(Long.class))
						parameter[i] = Long.parseLong(s);
					else if (clazz.equals(byte.class) || clazz.equals(Byte.class))
						parameter[i] = Byte.parseByte(s);
					else if (clazz.equals(short.class) || clazz.equals(Short.class))
						parameter[i] = Short.parseShort(s);
					else
						parameter[i] = s;
				} else
					parameter[i] = s;
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
