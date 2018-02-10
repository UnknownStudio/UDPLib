package team.unstudio.udpl.core.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.util.ReflectionUtils;
import team.unstudio.udpl.util.ServerUtils;

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
	
	@Command(value = "info", permission = "udpl.info")
	public void info(CommandSender sender){
		sender.sendMessage("----------UDPL Infomation----------");
		sender.sendMessage("UDPL Version: " + UDPLib.getInstance().getDescription().getVersion());
		sender.sendMessage("Minecraft Version: " + ServerUtils.getMinecraftVersion());
		sender.sendMessage("Bukkit Version: " + ReflectionUtils.PackageType.getServerVersion());
		sender.sendMessage("ProtocolLib Version: " + Bukkit.getPluginManager().getPlugin("ProtocolLib").getDescription().getVersion());
	}
}
