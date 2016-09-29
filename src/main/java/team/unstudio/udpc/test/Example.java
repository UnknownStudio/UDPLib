package team.unstudio.udpc.test;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import team.unstudio.udpc.api.command.CommandManager;
import team.unstudio.udpc.api.command.SubCommand;
import team.unstudio.udpc.api.ui.Button;
import team.unstudio.udpc.api.ui.UIFactory;
import team.unstudio.udpc.core.UDPCore;

public class Example {
	
	public static final Example INSTANCE = new Example();
	
	public void onLoad() {
	}
	
	@SuppressWarnings("unchecked")
	public void onEnable() {
		new CommandManager("udpc", UDPCore.INSTANCE).addSub(new SubCommand() {
			
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				sender.sendMessage(UDPCore.NAME+" "+UDPCore.VERSION);
				return true;
			}
		}.setSub("help")).addSub(new SubCommand() {
			
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				UIFactory.createUI(54, "测试用界面").addButton(new Button(0) {
					
					@Override
					public void onClick(InventoryClickEvent event) {
						event.getWhoClicked().sendMessage("点击按钮1");
					}
				}.setCanOperate(true)).addButton(new Button(1) {
					
					@Override
					public void onClick(InventoryClickEvent event) {
						event.getWhoClicked().sendMessage("点击按钮2");
					}
				}.setCanOperate(false)).open((HumanEntity) sender, UDPCore.INSTANCE);
				return true;
			}
		}.setSub("ui").setSenders(Player.class)).registerCommand();
	}
	
	public void onDisable() {
	}
}
