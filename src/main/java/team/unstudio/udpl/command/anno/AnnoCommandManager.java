package team.unstudio.udpl.command.anno;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpl.command.CommandHelper;
import team.unstudio.udpl.util.ServerHelper;

public class AnnoCommandManager implements CommandExecutor,TabCompleter{
	
	private final JavaPlugin plugin;
	private final String name;
	private final CommandWrapper defaultCommand;
	
	private String noPermissionMessage = "没有足够的权限";
	private String noEnoughParameterMessage = "参数不足";
	private String wrongSenderMessage = "错误的指令发送者";
	private String errorParameterMessage = "参数错误";
	private String unknownCommandMessage = "未知的指令";
	private String runCommandFailureMessage = "指令执行失败";
	
	private String usage;
	private String description;
	
	/**
	 * 创建指令管理者
	 * @param name 指令名
	 */
	public AnnoCommandManager(@Nonnull String name){
		this(name,(JavaPlugin) Bukkit.getPluginCommand(name).getPlugin());
	}

	/**
	 * 创建指令管理者
	 * @param name 指令名
	 * @param plugin 插件
	 */
	public AnnoCommandManager(@Nonnull String name,JavaPlugin plugin){
		this.name = name;
		this.plugin = plugin;
		this.defaultCommand = new CommandWrapper(name,this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==0){
			handleCommand(defaultCommand, sender, command, label, args);
			return true;
		}
		
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		int index=0;
		List<CommandWrapper> children = defaultCommand.getChildren();
		while(!children.isEmpty()&&index<toLowerCaseArgs.length)
			for(CommandWrapper wrapper:children){
				if(!wrapper.getNode().equals(toLowerCaseArgs[index])) 
					continue;
				
				index++;
				children = wrapper.getChildren();
				
				if (children.isEmpty()) {
					handleCommand(wrapper, sender, command, label ,Arrays.copyOfRange(args, index, args.length));
					return true;
				}
				break;
			}
		
		onUnknownCommand(sender, command, label, args);
		return true;
	}
	
	private void handleCommand(CommandWrapper wrapper,CommandSender sender,Command command,String label,String args[]){
		switch (wrapper.onCommand(sender, args)) {
		case ErrorParameter:
			onErrorParameter(sender, command, label, args, wrapper);
			break;
		case NoEnoughParameter:
			onNoEnoughParameter(sender, command, label, args, wrapper);
			break;
		case NoPermission:
			onNoPermission(sender, command, label, args, wrapper);
			break;
		case WrongSender:
			onWrongSender(sender, command, label, args, wrapper);
			break;
		case Failure:
			onRunCommandFailure(sender, command, label, args, wrapper);
			break;
		case Success:
			break;
		}
	}

	/**
	 * 添加指令
	 */
	public AnnoCommandManager addCommand(Object object){
		for(Method method:object.getClass().getDeclaredMethods()){
			team.unstudio.udpl.command.anno.Command annoCommand = method.getAnnotation(team.unstudio.udpl.command.anno.Command.class);
			
			if(annoCommand==null) 
				continue;
			
			getCommandWrapper(annoCommand.value()).setMethod(object, method);

			for(Alias annoAlias:method.getAnnotationsByType(Alias.class))
				getCommandWrapper(annoAlias.value()).setMethod(object, method);
		}
		return this;
	}
	
	public AnnoCommandManager addAllCommand(Object ...object){
		Arrays.stream(object).forEach(this::addCommand);
		return this;
	}
	
	public CommandWrapper getCommandWrapper(String args[]){
		if(args.length==0)
			return defaultCommand;
		
		args = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		CommandWrapper parent = null;
		for (CommandWrapper command : defaultCommand.getChildren())
			if (command.getNode().equals(args[0])) {
				parent = command;
				break;
			}

		if (parent == null) {
			parent = new CommandWrapper(args[0], this);
			defaultCommand.getChildren().add(parent);
		}

		label: for (int i = 1; i < args.length; i++) {
			for (CommandWrapper command : parent.getChildren()) {
				if (parent.getNode().equals(args[i])) {
					parent = command;
					continue label;
				}
			}
			
			CommandWrapper command = new CommandWrapper(args[i], this);
			parent.getChildren().add(command);
			parent = command;
		}
		return parent;
	}

	public String getNoPermissionMessage() {
		return noPermissionMessage;
	}

	public AnnoCommandManager setNoPermissionMessage(String def) {
		this.noPermissionMessage = def;
		return this;
	}

	public String getNoEnoughParameterMessage() {
		return noEnoughParameterMessage;
	}

	public AnnoCommandManager setNoEnoughParameterMessage(String def) {
		this.noEnoughParameterMessage = def;
		return this;
	}

	public String getWrongSenderMessage() {
		return wrongSenderMessage;
	}

	public AnnoCommandManager setWrongSenderMessage(String def) {
		this.wrongSenderMessage = def;
		return this;
	}
	
	public String getErrorParameterMessage() {
		return errorParameterMessage;
	}

	public AnnoCommandManager setErrorParameterMessage(String def) {
		this.errorParameterMessage = def;
		return this;
	}

	public void onNoPermission(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(noPermissionMessage);
	}
	
	public void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(noEnoughParameterMessage);
	}
	
	public void onWrongSender(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(wrongSenderMessage);
	}
	
	public void onErrorParameter(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(errorParameterMessage);
	}
	
	public void onUnknownCommand(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(unknownCommandMessage);
	}
	
	public void onRunCommandFailure(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(runCommandFailureMessage);
	}
	
	public String getUnknownCommandMessage() {
		return unknownCommandMessage;
	}

	public AnnoCommandManager setUnknownCommandMessage(String def) {
		this.unknownCommandMessage = def;
		return this;
	}

	public String getRunCommandFailureMessage() {
		return runCommandFailureMessage;
	}

	public AnnoCommandManager setRunCommandFailureMessage(String def) {
		this.runCommandFailureMessage = def;
		return this;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}
	
	/**
	 * 注册指令
	 */
	public AnnoCommandManager registerCommand(){
		PluginCommand command = plugin.getCommand(name);
		command.setExecutor(this);
		command.setTabCompleter(this);
		return this;
	}
	
	/**
	 * 不安全的注册指令
	 */
	public AnnoCommandManager unsafeRegisterCommand(){
		PluginCommand command = CommandHelper.unsafeRegisterCommand(name, plugin);
		command.setExecutor(this);
		command.setTabCompleter(this);
		return this;
	}

	public String getName() {
		return name;
	}
	
	public String getUsage() {
		return usage;
	}

	public void setUsage(String usage) {
		this.usage = usage;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		List<String> list = new ArrayList<>();
		
		List<CommandWrapper> children = defaultCommand.getChildren();
		int i , size = toLowerCaseArgs.length-1;
		label : for(i = 0 ; !children.isEmpty() && i < size ; i++){
			for(CommandWrapper commandWrapper:children){
				if(commandWrapper.getNode().equals(toLowerCaseArgs[i])){
					i++;
					children = commandWrapper.getChildren();
					if(!children.isEmpty())
						continue label;
					
					list.addAll(commandWrapper.onTabComplete(Arrays.copyOfRange(args, i, args.length)));
					break label;
				}
			}
		}
		
		for (CommandWrapper commandWrapper : children)
			if (commandWrapper.getNode().startsWith(toLowerCaseArgs[size]))
				list.add(commandWrapper.getNode());
		
		if(list.isEmpty()){
			String prefix = args[size];
			Collections.addAll(list, ServerHelper.getOnlinePlayerNamesWithFilter(name->name.startsWith(prefix)));
		}
		
		return list;
	}
}
