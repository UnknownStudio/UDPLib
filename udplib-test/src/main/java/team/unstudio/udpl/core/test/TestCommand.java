package team.unstudio.udpl.core.test;

import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import team.unstudio.udpl.area.Area;
import team.unstudio.udpl.command.anno.*;
import team.unstudio.udpl.config.ConfigurationHelper;
import team.unstudio.udpl.conversation.Conversation;
import team.unstudio.udpl.conversation.request.*;
import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.nms.NmsHelper;
import team.unstudio.udpl.nms.tileentity.NmsMobSpawner;
import team.unstudio.udpl.nms.tileentity.NmsTileEntity;
import team.unstudio.udpl.scoreboard.BiScoreboard;
import team.unstudio.udpl.util.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

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
						@Required(complete= {"a","b"}) int x1,
						@Required int y1,
						@Required int z1,
						@Required int x2,
						@Required int y2,
						@Required int z2){
		TestLoader.areaManager.addArea(new Area(new Location(sender.getWorld(), x1, y1, z1), new Location(sender.getWorld(), x2, y2, z2)));
	}
	
	@Command(value = "send", senders = Player.class)
	public void send(Player sender,@Required String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	@Command(value = "sendjson", senders = Player.class)
	public void sendjson(Player sender,@Required String message){
		sender.spigot().sendMessage(ComponentSerializer.parse(message));
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
	
	@Command(value = "conversation", senders = Player.class)
	public void conversation(Player sender){
		new Conversation(UDPLib.getInstance())
				.addRequest(RequestString.newRequestString().setPrompt("请在聊天框输入一个消息."))
				.addRequest(RequestBlock.newRequestBlock().setPrompt("请点击一个方块."))
				.addRequest(RequestEntity.newRequestEntity().setPrompt("请点击一个实体."))
				.addRequest(RequestBigDecimal.newRequestBigDecimal().setPrompt("请输入一个数字."))
				.addRequest(RequestChoice.newRequestChoice().addItem("我要女装","拒绝女装").setPrompt("请点击下面的选项."))
				.addRequest(RequestMultiChoice.newRequestMultiChoice().setEndItem("结束").addItem("选项1","选项2").setPrompt("请点击下面的选项."))
				.addRequest(RequestMultiString.newRequestMultiString("end").setPrompt("请输入多行文字，最后输入 end 结束输入."))
				.addRequest(RequestChooseItemStack.newRequestChooseItemStack(InventoryType.CHEST, "请选择一个物品").addItem(new ItemStack(Material.STONE),new ItemStack(Material.GRASS)))
				.addRequest(RequestChooseItemStackLarge.newRequestChooseItemStackLarge().setTitle("请选择一个物品").setNextPageItem(new ItemStack(Material.WOOL)).setLastPageItem(new ItemStack(Material.WOOD)).addItem(new ItemStack(Material.STONE),new ItemStack(Material.GRASS)))
				.addRequest(RequestConfirm.newRequestConfirm().setPrompt("请在10秒内输入confirm以确认操作.").setTimeout(10).setTimeoutMessage("操作已超时!"))
				.setOnComplete(con->{
					con.getPlayer().sendMessage(con.getRequest(0).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(1).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(2).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(3).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(4).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(5).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(6).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(7).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(8).getResult().get().toString());
					con.getPlayer().sendMessage(con.getRequest(9).getResult().get().toString());
				}).start(sender);
	}
	
	@Command("serialize")
	public void serialize(CommandSender sender) throws IOException{
		File file = new File(UDPLib.getInstance().getDataFolder(),"test.yml");
		FileConfiguration configuration = ConfigurationHelper.loadConfiguration(file);
		configuration.set("test", new TestSerialization(233));
		configuration.save(file);
		configuration = ConfigurationHelper.loadConfiguration(file);
		sender.sendMessage(Integer.toString(((TestSerialization)configuration.get("test")).getItem()));
	}
	
	@Command(value = "permission", senders = Player.class, permission = "udpl.test.permission")
	public void permission(Player sender){
		sender.sendMessage(PlayerUtils.getLanguage(sender));
	}
	
	@TabComplete(value = "permission")
	public List<String> tabComplete(String[] args){
		return Collections.singletonList("TabComplete");
	}
}
