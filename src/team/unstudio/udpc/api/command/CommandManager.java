package team.unstudio.udpc.api.command;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 指令管理者
 */
public class CommandManager implements CommandExecutor,TabCompleter{

	private final JavaPlugin plugin;
	private final String name;
	private final List<CommandHandler> handlers = new ArrayList<>();
	
	private String noPermissionMessage = "";
	private String noEnoughParameterMessage = "";
	private String wrongSenderMessage = "";
	private String errorParameterMessage = "";
	private String unknownCommandMessage = "";
	private String runCommandFailureMessage = "";
	
	/**
	 * 创建指令管理者
	 * @param name 指令
	 * @param plugin 插件
	 */
	public CommandManager(String name,JavaPlugin plugin){
		this.name = name;
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for (CommandHandler h : handlers) {
			if (h.getSub().length > args.length)
				continue;

			boolean flag = false;
			int j = h.getSub().length;
			for (int i = 0; i < j; i++) {
				if (!args[i].equalsIgnoreCase(h.getSub()[i])) {
					flag = true;
					break;
				}
			}
			if (flag)
				continue;

			flag = true;
			for (Class<? extends CommandSender> s : h.getSenders()) {
				if (s.isAssignableFrom(sender.getClass())) {
					flag = false;
					break;
				}
			}
			if (flag) {
				onWrongSender(sender, command, label, args, h);
				return true;
			}

			if (h.getPermission() != null && !sender.hasPermission(h.getPermission())) {
				onNoPermission(sender, command, label, args, h);
				return true;
			}

			if (j + h.getParameterTypes().length > args.length) {
				onNoEnoughParameter(sender, command, label, args, h);
				return true;
			}

			Object[] objs = new Object[args.length - j];
			for (int i = 0; i < args.length - j; i++) {
				try {
					String s = args[j + i];
					if (i < h.getParameterTypes().length) {
						Class<?> clazz = h.getParameterTypes()[i];
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
					onErrorParameter(sender, command, label, args, h);
					return true;
				}
			}

			if (h.onCommand(sender, objs))
				onRunCommandFailure(sender, command, label, args, h);
			return true;
		}
		onUnknownCommand(sender, command, label, args);
		return true;
	}

	/**
	 * 添加指令处理者
	 * @param handler
	 * @return
	 */
	public CommandManager addHandler(CommandHandler handler){
		handlers.add(handler);
		return this;
	}
	
	/**
	 * 获取全部处理者
	 * @return
	 */
	public CommandHandler[] getHandlers(){
		return handlers.toArray(new CommandHandler[0]);
	}

	public String getNoPermissionMessage() {
		return noPermissionMessage;
	}

	public CommandManager setNoPermissionMessage(String def) {
		this.noPermissionMessage = def;
		return this;
	}

	public String getNoEnoughParameterMessage() {
		return noEnoughParameterMessage;
	}

	public CommandManager setNoEnoughParameterMessage(String def) {
		this.noEnoughParameterMessage = def;
		return this;
	}

	public String getWrongSenderMessage() {
		return wrongSenderMessage;
	}

	public CommandManager setWrongSenderMessage(String def) {
		this.wrongSenderMessage = def;
		return this;
	}
	
	public String getErrorParameterMessage() {
		return errorParameterMessage;
	}

	public CommandManager setErrorParameterMessage(String def) {
		this.errorParameterMessage = def;
		return this;
	}

	private void onNoPermission(CommandSender sender, Command command, String label, String[] args, CommandHandler handler){
		sender.sendMessage(noPermissionMessage);
	}
	
	private void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args, CommandHandler handler){
		sender.sendMessage(noEnoughParameterMessage);
		StringBuilder builder = new StringBuilder();
		for(String s:handler.getSub()) builder.append(s+" ");
		sender.sendMessage(String.format("Usage: /%s %s %s",label,builder.substring(0, builder.length()-1),handler.getUsage()));
	}
	
	private void onWrongSender(CommandSender sender, Command command, String label, String[] args, CommandHandler handler){
		sender.sendMessage(wrongSenderMessage);
	}
	
	private void onErrorParameter(CommandSender sender, Command command, String label, String[] args, CommandHandler handler){
		sender.sendMessage(errorParameterMessage);
		StringBuilder builder = new StringBuilder();
		for(String s:handler.getSub()) builder.append(s+" ");
		sender.sendMessage(String.format("Usage: /%s %s %s",label,builder.substring(0, builder.length()-1),handler.getUsage()));
	}
	
	private void onUnknownCommand(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(unknownCommandMessage);
	}
	
	private void onRunCommandFailure(CommandSender sender, Command command, String label, String[] args, CommandHandler handler){
		sender.sendMessage(runCommandFailureMessage);
	}
	
	public String getUnknownCommandMessage() {
		return unknownCommandMessage;
	}

	public CommandManager setUnknownCommandMessage(String def) {
		this.unknownCommandMessage = def;
		return this;
	}

	public String getRunCommandFailureMessage() {
		return runCommandFailureMessage;
	}

	public CommandManager setRunCommandFailureMessage(String def) {
		this.runCommandFailureMessage = def;
		return this;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * 注册指令
	 */
	public CommandManager registerCommand(){
		plugin.getCommand(name).setExecutor(this);
		plugin.getCommand(name).setTabCompleter(this);
		return this;
	}

	public String getName() {
		return name;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> list = new ArrayList<>();
		
		for(CommandHandler h:handlers){	
			int j = h.getSub().length;
			
			//判断是不是该补全
			boolean flag = false;
			for(int i=0;i<args.length-1&&i<j;i++){
				if(!h.getSub()[i].equalsIgnoreCase(args[i])){
					flag = true;
					break;
				}
			}
			if(flag)continue;
			
			//判断最后一个是不是完整的
			if(args.length<=j){
				if(args[args.length-1].isEmpty()||h.getSub()[args.length-1].startsWith(args[args.length-1]))list.add(h.getSub()[args.length-1]);
			}else{ //参数多余Sub
				String st[] = new String[args.length-j];
				for(int i=0;i<args.length-j;i++) st[i]=args[j+i];
				list.addAll(h.onTabComplete(args));
			}
		}
		return list;
	}
}
