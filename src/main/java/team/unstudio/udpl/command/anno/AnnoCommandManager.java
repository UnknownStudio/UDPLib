package team.unstudio.udpl.command.anno;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.collect.Lists;

import team.unstudio.udpl.command.CommandHelper;
import team.unstudio.udpl.util.ServerUtils;

public class AnnoCommandManager implements CommandExecutor,TabCompleter{
	
	private static final String unknownCommandMessage = ChatColor.RED+"未知的指令，输入 /%1$s help 查看帮助.";
	private static final String noPermissionMessage = ChatColor.RED+"没有足够的权限:"+ChatColor.YELLOW+" %1$s .";
	private static final String noEnoughParameterMessage = ChatColor.RED+"参数不足! 正确的使用方法: %1$s";
	private static final String wrongSenderMessage = ChatColor.RED+"该指令不能由该指令发送者发送!";
	private static final String errorParameterMessage = ChatColor.RED+"参数错误! 正确的使用方法: %1$s";
	private static final String runCommandFailureMessage = ChatColor.RED+"指令执行失败, 请在控制台查看更多的错误信息.";
	
	private final JavaPlugin plugin;
	private final String name;
	private final CommandWrapper defaultHandler;
	
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
		this.defaultHandler = new CommandWrapper(name,this,null);
	}
	
	public JavaPlugin getPlugin() {
		return plugin;
	}
	
	public String getName() {
		return name;
	}
	
	public String getUsage() {
		return usage;
	}

	public AnnoCommandManager setUsage(String usage) {
		this.usage = usage;
		return this;
	}
	
	public String getDescription() {
		return description;
	}

	public AnnoCommandManager setDescription(String description) {
		this.description = description;
		return this;
	}
	
	protected void onUnknownCommand(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(String.format(unknownCommandMessage,label));
	}

	protected void onNoPermission(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(String.format(noPermissionMessage,handler.getPermission()));
	}
	
	protected void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		StringBuilder builder = new StringBuilder(ChatColor.WHITE+"/");
		
		{
			Stack<String> subCommandStack = new Stack<>();
			CommandWrapper wrapper = handler;
			while(wrapper != null){
				subCommandStack.push(wrapper.getNode());
				wrapper = wrapper.getParent();
			}
			
			while(!subCommandStack.isEmpty()){
				builder.append(subCommandStack.pop());
				builder.append(" ");
			}
		}
		
		{
			String[] requiredUsages = handler.getRequiredUsages();
			for (int i = 0, size = args.length; i < size; i++) {
				builder.append("<");
				builder.append(requiredUsages[i]);
				builder.append("> ");
			}
			builder.append(ChatColor.RED);
			for (int i = args.length, size = requiredUsages.length; i < size; i++) {
				builder.append("<");
				builder.append(requiredUsages[i]);
				builder.append("> ");
			}
		}
		
		{
			builder.append(ChatColor.WHITE);
			String[] optionalUsages = handler.getOptionalUsages();
			for (int i = 0, size = optionalUsages.length; i < size; i++) {
				builder.append("[");
				builder.append(optionalUsages[i]);
				builder.append("] ");
			}
		}
		
		sender.sendMessage(String.format(noEnoughParameterMessage,builder.toString()));
	}
	
	protected void onWrongSender(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(wrongSenderMessage);
	}
	
	protected void onErrorParameter(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler, int[] errorParameterIndexs){
		StringBuilder builder = new StringBuilder(ChatColor.WHITE+"/");
		
		{
			Stack<String> subCommandStack = new Stack<>();
			CommandWrapper wrapper = handler;
			while(wrapper != null){
				subCommandStack.push(wrapper.getNode());
				wrapper = wrapper.getParent();
			}
			
			while(!subCommandStack.isEmpty()){
				builder.append(subCommandStack.pop());
				builder.append(" ");
			}
		}
		
		List<Integer> errorParameterIndexsList = Lists.newArrayList();
		for(int i:errorParameterIndexs)
			errorParameterIndexsList.add(i);
		
		{
			String[] requiredUsages = handler.getRequiredUsages();
			for (int i = 0, size = requiredUsages.length; i < size; i++) {
				if(errorParameterIndexsList.contains(i))
					builder.append(ChatColor.RED);
				else
					builder.append(ChatColor.WHITE);
				builder.append("<");
				builder.append(requiredUsages[i]);
				builder.append("> ");
			}
		}
		
		{
			String[] optionalUsages = handler.getOptionalUsages();
			for (int i = 0, size = optionalUsages.length,requiredLength = handler.getRequiredUsages().length; i < size; i++) {
				if(errorParameterIndexsList.contains(requiredLength+i))
					builder.append(ChatColor.RED);
				else
					builder.append(ChatColor.WHITE);
				builder.append("[");
				builder.append(optionalUsages[i]);
				builder.append("] ");
			}
		}
		
		sender.sendMessage(String.format(errorParameterMessage,builder.toString()));
	}
	
	protected void onRunCommandFailure(CommandSender sender, Command command, String label, String[] args, CommandWrapper handler){
		sender.sendMessage(runCommandFailureMessage);
	}
	
	/**
	 * 参数转换
	 * @param clazz 目标类型
	 * @param value 参数
	 * @return
	 */
	@SuppressWarnings("deprecation")
	protected Object transformParameter(Class<?> clazz, String value) {
		try {
			if (value == null || value.isEmpty())
				return null;
			else if (clazz.equals(String.class))
				return value;
			else if (clazz.equals(int.class) || clazz.equals(Integer.class))
				return Integer.parseInt(value);
			else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
				return Boolean.parseBoolean(value);
			else if (clazz.equals(float.class) || clazz.equals(Float.class))
				return Float.parseFloat(value);
			else if (clazz.equals(double.class) || clazz.equals(Double.class))
				return Double.parseDouble(value);
			else if (clazz.equals(long.class) || clazz.equals(Long.class))
				return Long.parseLong(value);
			else if (clazz.equals(byte.class) || clazz.equals(Byte.class))
				return Byte.parseByte(value);
			else if (clazz.equals(short.class) || clazz.equals(Short.class))
				return Short.parseShort(value);
			else if (clazz.equals(Player.class))
				return Bukkit.getPlayerExact(value);
			else if (clazz.equals(OfflinePlayer.class))
				return Bukkit.getOfflinePlayer(value);
			else if (clazz.equals(Material.class))
				return Material.valueOf(value);
		} catch (Exception e) {
		}
		return null;
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
		CommandHelper.unsafeRegisterCommand(name, plugin).ifPresent(command->{
			command.setExecutor(this);
			command.setTabCompleter(this);
		});
		return this;
	}
	
	@Override
	public final boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		CommandWrapper parent = defaultHandler;
		int i, size;
		for (i = 0,size = toLowerCaseArgs.length; i < size; i++) {
			CommandWrapper wrapper = parent.getChildren().get(toLowerCaseArgs[i]);
			if(wrapper == null)
				break;
			
			parent = wrapper;
		}
		
		handleCommand(parent, sender, command, label, Arrays.copyOfRange(args, i+1, args.length));
		return true;
	}
	
	protected void handleCommand(CommandWrapper wrapper,CommandSender sender,Command command,String label,String args[]){
		switch (wrapper.onCommand(sender,command,label,args)) {
		case UnknownCommand:
			onUnknownCommand(sender, command, label, args, wrapper);
			break;
		case ErrorParameter:
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
			
			createCommandWrapper(annoCommand.value()).setMethod(object, method);

			for(Alias annoAlias:method.getAnnotationsByType(Alias.class))
				createCommandWrapper(annoAlias.value()).setMethod(object, method);
		}
		return this;
	}
	
	public AnnoCommandManager addAllCommand(Object ...object){
		Arrays.stream(object).forEach(this::addCommand);
		return this;
	}
	
	public CommandWrapper getCommandWrapper(String[] args){
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		CommandWrapper parent = defaultHandler;
		for (int i = 0,size = toLowerCaseArgs.length; i < size; i++) {
			CommandWrapper wrapper = parent.getChildren().get(toLowerCaseArgs[i]);
			if(wrapper == null)
				break;
			
			parent = wrapper;
		}
		
		return parent;
	}
	
	private CommandWrapper createCommandWrapper(String[] args){
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		
		CommandWrapper parent = defaultHandler;
		for (int i = 0,size = toLowerCaseArgs.length; i < size; i++) {
			CommandWrapper wrapper = parent.getChildren().get(toLowerCaseArgs[i]);
			if(wrapper == null){
				wrapper = new CommandWrapper(toLowerCaseArgs[i], this, parent);
				parent.getChildren().put(wrapper.getNode(),wrapper);
			}
			
			parent = wrapper;
		}
		
		return parent;
	}

	@Override
	public final List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		String[] toLowerCaseArgs = Arrays.stream(args).map(String::toLowerCase).toArray(String[]::new);
		List<String> list = new ArrayList<>();
		
		CommandWrapper parent = defaultHandler;
		int i, size;
		for (i = 0,size = toLowerCaseArgs.length; i < size; i++) {
			CommandWrapper wrapper = parent.getChildren().get(toLowerCaseArgs[i]);
			if(wrapper == null)
				break;
			
			parent = wrapper;
		}
		list.addAll(parent.onTabComplete(Arrays.copyOfRange(args, i+1, args.length)));
		
		{ //Sub Commands
			String prefix = toLowerCaseArgs[size];
			parent.getChildren().keySet().stream().filter(node->node.startsWith(prefix)).forEach(list::add);
		}
		
		if(list.isEmpty()){ //Players
			String prefix = args[size];
			Collections.addAll(list, ServerUtils.getOnlinePlayerNamesWithFilter(name->name.startsWith(prefix)));
		}
		
		return list;
	}
}
