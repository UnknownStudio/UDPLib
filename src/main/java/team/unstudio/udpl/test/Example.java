package team.unstudio.udpl.test;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import team.unstudio.udpl.api.command.tree.CommandNode;
import team.unstudio.udpl.api.command.tree.TreeCommandManager;
import team.unstudio.udpl.api.ui.Slot;
import team.unstudio.udpl.api.ui.UIFactory;
import team.unstudio.udpl.core.UDPLib;

public class Example {
	
	public static final Example INSTANCE = new Example();
	
	public void onLoad() {
	}
	
	@SuppressWarnings("unchecked")
	public void onEnable() {
		new TreeCommandManager("udpc", UDPLib.getInstance()).addNode(new CommandNode() {
			
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				sender.sendMessage(UDPLib.NAME+" "+UDPLib.VERSION);
				return true;
			}
		}.setNode("help")).addNode(new CommandNode() {
			
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				UIFactory.createUI(54, "测试用界面").addSlot(new Slot(0) {
					
					@Override
					public void onClick(InventoryClickEvent event) {
						event.getWhoClicked().sendMessage("点击按钮1");
					}
				}.setAllowOperate(true)).addSlot(new Slot(1) {
					
					@Override
					public void onClick(InventoryClickEvent event) {
						event.getWhoClicked().sendMessage("点击按钮2");
					}
				}.setAllowOperate(false)).open((HumanEntity) sender, UDPLib.getInstance());
				return true;
			}
		}.setNode("ui").setSenders(Player.class)).registerCommand();
	}
	
	public void onDisable() {
	}
}
