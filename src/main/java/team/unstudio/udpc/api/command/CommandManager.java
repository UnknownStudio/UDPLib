package team.unstudio.udpc.api.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 指令管理者
 */
public class CommandManager implements CommandExecutor{

	private final JavaPlugin plugin;
	private final String name;
	
	private List<CommandHandler> handlers = new ArrayList<>();
	
	private String noPermissionMessage;
	private String noEnoughParameterMessage;
	private String wrongSenderMessage;
	private String errorParameterMessage;
	private String unknownCommandMessage;
	private String executionFailureMessage;
	
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
		for(CommandHandler h:handlers){
			for(Method m:h.getClass().getMethods()){
				
				m.setAccessible(true);
				team.unstudio.udpc.api.command.Command anno = m.getAnnotation(team.unstudio.udpc.api.command.Command.class);
				
				if(anno==null) continue;
				if(anno.value().length>args.length) continue;
				
				boolean flag = false;
				for(int i=0;i<anno.value().length;i++){
					if(!args[i].equalsIgnoreCase(anno.value()[i])){
						flag = true;
						break;
					}
				}
				if(flag) continue;
				
				for (Class<? extends CommandSender> s : anno.sender()) {
					if (!sender.getClass().isAssignableFrom(s)) {
						onWrongSender(sender, command, label, args, anno);
						return true;
					}
				}
				if (!sender.hasPermission(anno.permission())) {
					onNoPermission(sender, command, label, args, anno);
					return true;
				}
				if (anno.value().length + anno.parameter().length < args.length) {
					onNoEnoughParameter(sender, command, label, args, anno);
					return true;
				}
				Object[] objs = new Object[args.length - anno.value().length];
				for (int i = 0; i < args.length - anno.value().length; i++) {
					try {
						String s = args[anno.value().length + i];
						if(i<anno.parameter().length){
							Class<?> clazz = anno.parameter()[i];
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
						}else objs[i] = s;
					} catch (Exception e) {
						onErrorParameter(sender, command, label, args, anno);
						return true;
					}
				}

				if (m.getReturnType().equals(boolean.class)) {
					boolean result = false;
					try {
						result = (boolean) m.invoke(h, sender, objs);
					} catch (Exception e) {
					}
					if (!result)
						onExecutionFailure(sender, command, label, args, anno);
				} else {
					try {
						m.invoke(h, sender, objs);
					} catch (Exception e) {
						onExecutionFailure(sender, command, label, args, anno);
					}
				}
				return true;
			}
		}
		onUnknownCommand(sender, command, label, args);
		return true;
	}

	/**
	 * 添加指令处理者
	 * @param handler
	 * @return
	 */
	public CommandManager addCommandHandler(CommandHandler handler){
		handlers.add(handler);
		return this;
	}
	
	/**
	 * 移除指令处理者
	 * @param handler
	 * @return
	 */
	public boolean removeCommandHandler(CommandHandler handler){
		return handlers.remove(handler);
	}
	
	/**
	 * 获取全部处理者
	 * @return
	 */
	public CommandHandler[] getCommandHandlers(){
		return handlers.toArray(new CommandHandler[0]);
	}

	public String getNoPermissionMessage() {
		return noPermissionMessage;
	}

	public void setNoPermissionMessage(String def) {
		this.noPermissionMessage = def;
	}

	public String getNoEnoughParameterMessage() {
		return noEnoughParameterMessage;
	}

	public void setNoEnoughParameterMessage(String def) {
		this.noEnoughParameterMessage = def;
	}

	public String getWrongSenderMessage() {
		return wrongSenderMessage;
	}

	public void setWrongSenderMessage(String def) {
		this.wrongSenderMessage = def;
	}
	
	public String getErrorParameterMessage() {
		return errorParameterMessage;
	}

	public void setErrorParameterMessage(String def) {
		this.errorParameterMessage = def;
	}

	private void onNoPermission(CommandSender sender, Command command, String label, String[] args, team.unstudio.udpc.api.command.Command anno){
		sender.sendMessage(noPermissionMessage);
	}
	
	private void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args, team.unstudio.udpc.api.command.Command anno){
		sender.sendMessage(noEnoughParameterMessage);
	}
	
	private void onWrongSender(CommandSender sender, Command command, String label, String[] args, team.unstudio.udpc.api.command.Command anno){
		sender.sendMessage(wrongSenderMessage);
	}
	
	private void onErrorParameter(CommandSender sender, Command command, String label, String[] args, team.unstudio.udpc.api.command.Command anno){
		sender.sendMessage(errorParameterMessage);
	}
	
	private void onUnknownCommand(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(unknownCommandMessage);
	}
	
	private void onExecutionFailure(CommandSender sender, Command command, String label, String[] args, team.unstudio.udpc.api.command.Command anno){
		sender.sendMessage(executionFailureMessage);
	}
	
	public String getUnknownCommandMessage() {
		return unknownCommandMessage;
	}

	public void setUnknownCommandMessage(String def) {
		this.unknownCommandMessage = def;
	}

	public String getExecutionFailureMessage() {
		return executionFailureMessage;
	}

	public void setExecutionFailureMessage(String def) {
		this.executionFailureMessage = def;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * 注册指令
	 */
	public void registerCommand(){
		plugin.getCommand(name).setExecutor(this);
	}

	public String getName() {
		return name;
	}
}
