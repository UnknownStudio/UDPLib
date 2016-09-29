package team.unstudio.udpc.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {
	
	private String sub;
	private final List<SubCommand> subs = new ArrayList<>();
	private SubCommand parent;
	private String permission;
	@SuppressWarnings("unchecked")
	private Class<? extends CommandSender> senders[] = new Class[]{CommandSender.class};
	private Class<?>[] parameterTypes = new Class[0];
	private String usage = "";
	private String description = "";

	public String getSub() {
		return sub;
	}

	public SubCommand setSub(String sub) {
		this.sub = sub;
		return this;
	}
	
	public String getPermission() {
		return permission;
	}

	public SubCommand setPermission(String permission) {
		this.permission = permission;
		return this;
	}

	public Class<? extends CommandSender>[] getSenders() {
		return senders;
	}

	@SuppressWarnings("unchecked")
	public SubCommand setSenders(Class<? extends CommandSender> ...senders) {
		this.senders = senders;
		return this;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public SubCommand setParameterTypes(Class<?> ...parameterTypes) {
		this.parameterTypes = parameterTypes;
		return this;
	}

	public String getUsage() {
		return usage;
	}

	public SubCommand setUsage(String usage) {
		this.usage = usage;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public SubCommand setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public SubCommand addSub(SubCommand sub){
		subs.add(sub);
		sub.setParent(this);
		return this;
	}
	
	public List<SubCommand> getSubs(){
		return subs;
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
		for (int i = 0; i < args.length; i++) {
			try {
				String s = args[i];
				if (i < getParameterTypes().length) {
					Class<?> clazz = getParameterTypes()[i];
					if (clazz.equals(String.class))
						objs[i] = s;
					else if (clazz.equals(int.class))
						objs[i] = Integer.parseInt(s);
					else if (clazz.equals(boolean.class))
						objs[i] = Boolean.parseBoolean(s);
					else if (clazz.equals(float.class))
						objs[i] = Float.parseFloat(s);
					else if (clazz.equals(double.class))
						objs[i] = Double.parseDouble(s);
					else if (clazz.equals(long.class))
						objs[i] = Long.parseLong(s);
					else if (clazz.equals(byte.class))
						objs[i] = Byte.parseByte(s);
					else if (clazz.equals(short.class))
						objs[i] = Short.parseShort(s);
					else
						objs[i] = s;
				} else
					objs[i] = s;
			} catch (Exception e) {
				return CommandResult.ErrorParameter;
			}
		}
		
		if(!onCommand(sender, objs)) return CommandResult.Failure;
		else return CommandResult.Success;
	}
	
	public abstract boolean onCommand(CommandSender sender,Object[] args);
	
	public List<String> onTabComplete(String[] args){
		List<String> list = new ArrayList<>();
		for(Player player:Bukkit.getOnlinePlayers())if(player.getName().startsWith(args[0]))list.add(player.getName());
		return list;
	}

	public SubCommand getParent() {
		return parent;
	}

	public void setParent(SubCommand parent) {
		this.parent = parent;
	}
}
