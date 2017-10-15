package team.unstudio.udpl.core.test;

import org.bukkit.entity.Player;
import team.unstudio.udpl.command.anno.Alias;
import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.command.anno.Optional;
import team.unstudio.udpl.command.anno.Required;
import team.unstudio.udpl.util.Title;

public final class TestCommand {
	
	@Command(senders = Player.class)
	@Alias({"help"})
	public boolean help(Player sender){
		sender.sendMessage("UDPL Test 已正常启动.");
		return true;
	}

	@Command(value = { "title" }, senders = Player.class)
	public void title(Player sender, @Required String title, @Required String subTitle, @Optional("5") int fadeIn,
			@Optional("10") int stay, @Optional("5") int fadeOut) {
		Title.title(sender, title, subTitle, fadeIn, stay, fadeOut);
	}
}
