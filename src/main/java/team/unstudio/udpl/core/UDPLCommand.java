package team.unstudio.udpl.core;

import org.bukkit.command.CommandSender;

import team.unstudio.udpl.command.anno.Command;

public final class UDPLCommand {

	@Command(value = "debug", permission = "udpl.debug")
	public void debug(CommandSender sender) {
		if (UDPLib.isDebug()) {
			UDPLib.setDebug(false);
			sender.sendMessage("UDPL Debug disabled.");
		} else {
			UDPLib.setDebug(true);
			sender.sendMessage("UDPL Debug enabled.");
		}
	}
}
