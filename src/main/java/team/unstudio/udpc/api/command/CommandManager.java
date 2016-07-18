package team.unstudio.udpc.api.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandManager implements CommandExecutor{
	
	private List<CommandHandler> handlers = new ArrayList<>();
	private String defaultNoPermissionMessage;
	private String defaultNoEnoughParameterMessage;
	private String defaultWrongSenderMessage;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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

	public String getDefaultNoPermissionMessage() {
		return defaultNoPermissionMessage;
	}

	public void setDefaultNoPermissionMessage(String def) {
		this.defaultNoPermissionMessage = def;
	}

	public String getDefaultNoEnoughParameterMessage() {
		return defaultNoEnoughParameterMessage;
	}

	public void setDefaultNoEnoughParameterMessage(String def) {
		this.defaultNoEnoughParameterMessage = def;
	}

	public String getDefaultWrongSenderMessage() {
		return defaultWrongSenderMessage;
	}

	public void setDefaultWrongSenderMessage(String def) {
		this.defaultWrongSenderMessage = def;
	}
	
	public void onNoPermission(CommandSender sender, Command command, String label, String[] args){
		sender.sendMessage(defaultNoPermissionMessage);
	}
	
	public void onNoEnough
}
