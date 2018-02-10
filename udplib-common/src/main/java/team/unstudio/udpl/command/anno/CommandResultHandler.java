package team.unstudio.udpl.command.anno;

import java.util.Set;

import org.bukkit.command.CommandSender;

public interface CommandResultHandler {
	
	void setCommandManager(AnnoCommandManager commandManager);

	void onUnknownCommand(CommandSender sender, String[] args);
	
	void onWrongSender(CommandNode node, CommandSender sender, String[] args);
	
	void onNoPermission(CommandWrapper command, CommandSender sender, String[] args);
	
	void onNoEnoughParameter(CommandWrapper command, CommandSender sender, String[] args);
	
	void onWrongParameter(CommandWrapper command, CommandSender sender, String[] args, Set<Integer> wrongParameterIndexs);
	
	void onRunCommandFailure(CommandWrapper command, CommandSender sender, String[] args, Throwable t);
}
