package team.unstudio.udpl.command.anno;

import org.bukkit.command.CommandSender;

public class CommandHelpImpl {
	
	private final AnnoCommandManager manager;
	
	public CommandHelpImpl(AnnoCommandManager manager) {
		this.manager = manager;
	}
	
	public AnnoCommandManager getCommandManager() {
		return manager;
	}
	
	public void onHelp(CommandSender sender, String... value){
		java.util.Optional<CommandWrapper> optionalCommandWrapper = manager.getCommandWrapper(value);
		if(!optionalCommandWrapper.isPresent()){
			return;
		}
		
		CommandWrapper commandWrapper = optionalCommandWrapper.get();
		
	}
}
