package team.unstudio.udpl.core.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.command.anno.Alias;
import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.command.anno.Optional;
import team.unstudio.udpl.command.anno.Required;
import team.unstudio.udpl.command.anno.TabComplete;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.util.ActionBar;
import team.unstudio.udpl.util.BlockUtils;
import team.unstudio.udpl.util.BookUtils;
import team.unstudio.udpl.util.EntityUtils;
import team.unstudio.udpl.util.PlayerUtils;
import team.unstudio.udpl.util.PluginUtils;
import team.unstudio.udpl.util.SignUtils;
import team.unstudio.udpl.util.Title;

public final class TestCommand {
	
	@Command
	@Alias("help")
	public boolean help(CommandSender sender){
		sender.sendMessage("UDPL Test 已正常启动.");
		return true;
	}

	@Command(value = "title", senders = Player.class)
	public void title(Player sender, @Required(usage = "Title") String title,
			@Required(usage = "SubTitle") String subTitle, @Optional(value = "10", usage = "FadeIn") int fadeIn,
			@Optional(value = "20", usage = "Stay") int stay, @Optional(value = "10", usage = "FadeOut") int fadeOut) {
		Title.title(sender, title, subTitle, fadeIn, stay, fadeOut);
	}
	
	@Command(value = "actionbar", senders = Player.class)
	public void actionbar(Player sender, @Required(usage = "Text") String text){
		ActionBar.send(sender, text);
	}
	
	@Command(value = "blockbreakanima", senders = Player.class)
	public void blockBreakAnima(Player sender, @Required(usage = "State") byte state){
		BlockUtils.sendBlockBreakAnimation(sender, sender.getLocation().subtract(0, 1, 0), state);
	}
	
	@Command(value = "sign", senders = Player.class)
	public void sign(Player sender, @Required(usage = "Text") String text){
		SignUtils.open(sender, new String[]{text,"","",""});
	}
	
	@Command("save")
	public void saveDirectory(CommandSender sender){
		try {
			PluginUtils.saveDirectory(UDPLib.getInstance(), "lang", true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Command(value = "fakeitem", senders = Player.class)
	public void fakeItem(Player sender){
		EntityUtils.sendFakeItemEntity(sender, new ItemStack(Material.STONE), sender.getLocation(), "Stone");
	}
	
	@Command(value = "openbook", senders = Player.class)
	public void openBook(Player sender){
		BookUtils.open(sender, sender.getInventory().getItemInMainHand());
	}
	
	@Command(value = "permission", senders = Player.class, permission = "udpl.test.permission")
	public void permission(Player sender){
		sender.sendMessage(PlayerUtils.getLanguage(sender));
	}
	
	@TabComplete(value = "permission")
	public List<String> tabComplete(String[] args){
		return Arrays.asList("TabComplete");
	}
}
