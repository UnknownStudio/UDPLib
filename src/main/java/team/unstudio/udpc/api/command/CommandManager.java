package team.unstudio.udpc.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * 指令管理者
 */
public class CommandManager implements CommandExecutor,TabCompleter{

	private final JavaPlugin plugin;
	private final String name;
	private final List<SubCommand> subs = new ArrayList<>();
	
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
		int i=0;
		List<SubCommand> tsubs = subs;
		while(!tsubs.isEmpty()&&i<args.length){
			for(SubCommand s:tsubs){
				if(s.getSub().equalsIgnoreCase(args[i])){
					i++;
					tsubs = s.getSubs();
					if(tsubs.isEmpty()){
						String targs[] = new String[args.length-i];
						for(int j=0;j<targs.length;j++)targs[j]=args[i+j];
						switch (s.onCommand(sender, targs)) {
						case ErrorParameter:
							onErrorParameter(sender, command, label, args, s);
							break;
						case NoEnoughParameter:
							onNoEnoughParameter(sender, command, label, args, s);
							break;
						case NoPermission:
							onNoPermission(sender, command, label, args, s);
							break;
						case WrongSender:
							onWrongSender(sender, command, label, args, s);
							break;
						case Failure:
							onRunCommandFailure(sender, command, label, args, s);
							break;
						case Success:
							break;
						default:
							break;
						};
						return true;
					}
					break;
				}
			}
		}
		onUnknownCommand(sender, command, label, args);
		return true;
	}

	/**
	 * 添加指令子指令
	 * @param sub
	 * @return
	 */
	public CommandManager addSub(SubCommand sub){
		subs.add(sub);
		sub.setParent(null);
		return this;
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

	private void onNoPermission(CommandSender sender, Command command, String label, String[] args, SubCommand handler){
		sender.sendMessage(noPermissionMessage);
	}
	
	private void onNoEnoughParameter(CommandSender sender, Command command, String label, String[] args, SubCommand handler){
		sender.sendMessage(noEnoughParameterMessage);
	}
	
	private void onWrongSender(CommandSender sender, Command command, String label, String[] args, SubCommand handler){
		sender.sendMessage(wrongSenderMessage);
	}
	
	private void onErrorParameter(CommandSender sender, Command command, String label, String[] args, SubCommand handler){
		sender.sendMessage(errorParameterMessage);
	}
	
	private void onUnknownCommand(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(unknownCommandMessage);
	}
	
	private void onRunCommandFailure(CommandSender sender, Command command, String label, String[] args, SubCommand handler){
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
		
		int i=0;
		List<SubCommand> tsubs = subs;
		while(!tsubs.isEmpty()&&i<args.length-1){
			for(SubCommand s:tsubs){
				if(s.getSub().equalsIgnoreCase(args[i])){
					i++;
					tsubs = s.getSubs();
					if(tsubs.isEmpty()){
						String targs[] = new String[args.length-i];
						for(int j=0;j<targs.length;j++)targs[j]=args[j+i];
						list.addAll(s.onTabComplete(args));
					}
					break;
				}
			}
		}
		if(!tsubs.isEmpty())for(SubCommand s:tsubs)if(args[i].isEmpty()||s.getSub().startsWith(args[args.length-1]))list.add(s.getSub());
		
		if(list.isEmpty())for(Player player:Bukkit.getOnlinePlayers())if(player.getName().startsWith(args[0]))list.add(player.getName());
		return list;
	}
}
