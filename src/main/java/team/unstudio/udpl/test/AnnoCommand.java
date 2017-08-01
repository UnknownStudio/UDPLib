package team.unstudio.udpl.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.api.area.Area;
import team.unstudio.udpl.api.area.AreaManager;
import team.unstudio.udpl.api.command.anno.Alias;
import team.unstudio.udpl.api.command.anno.Command;
import team.unstudio.udpl.api.command.anno.Optional;
import team.unstudio.udpl.api.command.anno.Required;
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
	
	@Command(value = "give",senders = Player.class)
	public boolean giveItemToPlayer(CommandSender sender,@Required Player target,@Required Material itemType,@Optional("1") int amount,@Optional("0") short durability){
		if(target==null)
			return false;
		
		if(itemType==null)
			return false;
		
		ItemStack item = new ItemStack(itemType);
		item.setAmount(amount);
		item.setDurability(durability);
		
		target.getInventory().addItem(item);
		return true;
	}
	
	@Command(senders = Player.class)
	@Alias({"tpa"})
	@Alias({"teleport"})
	public boolean tpa(Player sender,@Required Player target){
		if(target==null||!target.isOnline())
			return false;
		
		sender.teleport(target);
		
		return true;
	}
	
	@Command(value = "area",senders = Player.class)
	public void area(Player sender){
		Area area = new CustomArea(new Location(Bukkit.getWorld("world"), 0, 0, 0), new Location(Bukkit.getWorld("world"), 100, 100, 100),sender);
		if(AreaManager.getWorldAreaManager(area.getWorld()).getAreas(area).size()==0)
			AreaManager.addArea(area);
	}
}
