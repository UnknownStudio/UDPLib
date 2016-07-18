package team.unstudio.udpc.api.command;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager implements CommandExecutor{

	private final JavaPlugin plugin;
	
	private List<CommandHandler> handlers = new ArrayList<>();
	
	private String noPermissionMessage;
	private String noEnoughParameterMessage;
	private String wrongSenderMessage;
	private String errorParameterMessage;
	private String unknownCommandMessage;
	private String executionFailureMessage;
	
	public CommandManager(JavaPlugin plugin){
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		for(CommandHandler h:handlers){
			for(Method m:h.getClass().getMethods()){
				m.setAccessible(true);
				team.unstudio.udpc.api.command.Command c = m.getAnnotation(team.unstudio.udpc.api.command.Command.class);
				if(c.value().length<args.length) continue;
				boolean flag = false;
				for(int i=0;i<c.value().length;i++){
					if(!args[i].equalsIgnoreCase(c.value()[i])){
						flag = true;
						break;
					}
				}
				
				if(flag) continue;
				else{
					
					for(Class<? extends CommandSender> s:c.sender()){
						if(!sender.getClass().isAssignableFrom(s)){
							onWrongSender(sender, command, label, args);
							return true;
						}
					}
					if(!sender.hasPermission(c.permission())){
						onNoPermission(sender, command, label, args);
						return true;
					}
					if(c.value().length+c.parameter().length<args.length){
						onNoEnoughParameter(sender, command, label, args);
						return true;
					}
					Object[] objs = new Object[args.length-c.value().length];
					for(int i=0;i<args.length-c.value().length;i++){
						try{
							String s = args[c.value().length+i];
							Class<?> clazz = c.parameter()[i];
							if(clazz.equals(String.class)) objs[i]=s;
							else if(clazz.equals(int.class)) objs[i]=Integer.parseInt(s);
							else if(clazz.equals(boolean.class)) objs[i]=Boolean.parseBoolean(s);
							else if(clazz.equals(float.class)) objs[i]=Float.parseFloat(s);
							else if(clazz.equals(double.class)) objs[i]=Double.parseDouble(s);
							else if(clazz.equals(long.class)) objs[i]=Long.parseLong(s);
							else if(clazz.equals(byte.class)) objs[i]=Byte.parseByte(s);
							else if(clazz.equals(short.class)) objs[i]=Short.parseShort(s);
							else objs[i]=s;
						}catch(Exception e){
							onErrorParameter(sender, command, label, args);
							return true;
						}
					}
					
					if(m.getReturnType().equals(boolean.class)){
						boolean result = false;
						try {
							result = (boolean) m.invoke(h,sender,objs);
						} catch (Exception e) {}
						finally {
							if(!result)onExecutionFailure(sender, command, label, args);
							}
					}else{
						try {
							m.invoke(h, sender,objs);
						} catch (Exception e) {
							onExecutionFailure(sender, command, label, args);
						}
					}
					return true;
				}
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
	public boolean addCommandHandler(CommandHandler handler){
		return handlers.add(handler);
	}
	
	/**
	 * 移除指令处理者
	 * @param handler
	 * @return
	 */
	public boolean removeCommandHandler(CommandHandler handler){
		return handlers.remove(handler);
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

	public void onNoPermission(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(noPermissionMessage);
	}
	
	public void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(noEnoughParameterMessage);
	}
	
	public void onWrongSender(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(wrongSenderMessage);
	}
	
	public void onErrorParameter(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(errorParameterMessage);
	}
	
	public void onUnknownCommand(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(unknownCommandMessage);
	}
	
	public void onExecutionFailure(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(executionFailureMessage);
	}

	public JavaPlugin getPlugin() {
		return plugin;
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
}
