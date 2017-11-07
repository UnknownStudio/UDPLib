package team.unstudio.udpl.core.test;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unstudio.udpl.area.Area;
import team.unstudio.udpl.command.anno.Alias;
import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.command.anno.Optional;
import team.unstudio.udpl.command.anno.Required;
import team.unstudio.udpl.command.anno.TabComplete;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.tileentity.NmsMobSpawner;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;
import team.unstudio.udpl.scoreboard.BiScoreboard;
import team.unstudio.udpl.util.ActionBar;
import team.unstudio.udpl.util.BlockUtils;
import team.unstudio.udpl.util.BookUtils;
import team.unstudio.udpl.util.EntityUtils;
import team.unstudio.udpl.util.PlayerUtils;
import team.unstudio.udpl.util.PluginUtils;
import team.unstudio.udpl.util.ReflectionUtils;
import team.unstudio.udpl.util.ServerUtils;
import team.unstudio.udpl.util.SignUtils;
import team.unstudio.udpl.util.Title;

public final class TestCommand {
	
	@Command
	@Alias("help")
	public boolean help(CommandSender sender,String[] args){
		sender.sendMessage("UDPL Test 已正常启动.");
		if(args.length > 0)
			sender.sendMessage("多参数测试成功.");
		return true;
	}
	
	@Command(value = "area", senders = Player.class)
	public void area(Player sender,
						@Required int x1,
						@Required int y1,
						@Required int z1,
						@Required int x2,
						@Required int y2,
						@Required int z2){
		TestLoader.areaManager.addArea(new Area(new Location(sender.getWorld(), x1, y1, z1), new Location(sender.getWorld(), x2, y2, z2)));
	}
	
	@Command(value = "language", senders = Player.class)
	public void language(Player sender){
		sender.sendMessage(PlayerUtils.getLanguage(sender));
	}
	
	@Command(value = "nmsitem", senders = Player.class)
	public void nmsitem(Player sender){
		sender.sendMessage(NmsHelper.createNmsItemStack(sender.getInventory().getItemInMainHand()).save().toString());
	}
	
	@Command(value = "scoreboard", senders = Player.class)
	public void scoreboard(Player sender){
		 BiScoreboard scoreboard = new BiScoreboard();
		 scoreboard.put("UDPL Test", 16);
		 scoreboard.put("UDPL Version " + UDPLib.getInstance().getDescription().getVersion(), 15);
		 scoreboard.put("NMS Version " + ReflectionUtils.PackageType.getServerVersion(), 14);
		 scoreboard.put("MC Version " + ServerUtils.getMinecraftVersion(), 13);
		 scoreboard.put("Language " + PlayerUtils.getLanguage(sender), 12);
		 scoreboard.display(sender);
	}

	@Command(value = "title", senders = Player.class)
	public void title(Player sender, @Required(name = "Title") String title,
			@Required(name = "SubTitle") String subTitle, @Optional(value = "10", name = "FadeIn") int fadeIn,
			@Optional(value = "20", name = "Stay") int stay, @Optional(value = "10", name = "FadeOut") int fadeOut) {
		Title.title(sender, title, subTitle, fadeIn, stay, fadeOut);
	}
	
	@Command(value = "actionbar", senders = Player.class)
	public void actionbar(Player sender, @Required(name = "Text") String text){
		ActionBar.send(sender, text);
	}
	
	@Command(value = "blockbreakanima", senders = Player.class)
	public void blockBreakAnima(Player sender, @Required(name = "State") byte state){
		BlockUtils.sendBlockBreakAnimation(sender, sender.getLocation().subtract(0, 1, 0), state);
	}
	
	@Command(value = "sign", senders = Player.class)
	public void sign(Player sender, @Required(name = "Text") String text){
		SignUtils.open(sender, new String[]{text,"","",""});
	}
	
	@Command("save")
	public void saveDirectory(CommandSender sender){
		PluginUtils.saveDirectory(UDPLib.getInstance(), "lang", true);
	}
	
	@Command(value = "fakeitem", senders = Player.class)
	public void fakeItem(Player sender){
		EntityUtils.sendFakeItemEntity(sender, new ItemStack(Material.STONE), sender.getLocation(), "Stone");
	}
	
	@Command(value = "openbook", senders = Player.class)
	public void openBook(Player sender){
		BookUtils.open(sender, sender.getInventory().getItemInMainHand());
	}
	
	@Command(value = "spawner", senders = Player.class)
	public void spawner(Player sender){
		Location location = sender.getLocation().subtract(0, 1, 0);
		BlockState spawnerCreature = location.getBlock().getState();
		NmsMobSpawner nmsSpawner = (NmsMobSpawner) NmsTileEntity.createNmsTileEntity(spawnerCreature);
		sender.sendMessage(nmsSpawner.save().toString());
		sender.sendMessage(Short.toString(nmsSpawner.getSpawnCount()));
		nmsSpawner.setSpawnCount((short) 10);
		sender.sendMessage(Short.toString(nmsSpawner.getSpawnCount()));
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
