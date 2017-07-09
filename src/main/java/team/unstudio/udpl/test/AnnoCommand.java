package team.unstudio.udpl.test;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import team.unstudio.udpl.api.command.anno.Command;
import team.unstudio.udpl.api.ui.UI;
import team.unstudio.udpl.api.ui.UIFactory;

public final class AnnoCommand {

	UI ui;
	Player player;
	
	@Command(value = "opengui", 
			senders = Player.class)
	public void openGUI(Player sender) {
		ui = UIFactory.createUI(9, "TestUI");
		ui.open(sender);
		player = sender;
	}
	
	@Command(value = "closegui", 
			senders = ConsoleCommandSender.class)
	public void closeGUI(ConsoleCommandSender sender) {
		ui.close(player);
	}
}
