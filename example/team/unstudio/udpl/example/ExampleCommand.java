package team.unstudio.udpl.example;
import org.bukkit.entity.Player;

import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.command.anno.Optional;
import team.unstudio.udpl.command.anno.Required;

public class ExampleCommand {
	
	@Command(value = {}, // 指令名
			senders = Player.class, // 接收的指令发送者类型
			permission = "example.sendmessage")// 执行指令必须的权限
	public void example(Player player) { // 可选参数，value是默认值，usage是参数的用法
		player.sendMessage(ExamplePlugin.I18N.format(player, "example.i18n"));
	}
	
	@Command(value = "sendmessage", // 指令名
			senders = Player.class, // 接收的指令发送者类型
			permission = "example.sendmessage")// 执行指令必须的权限
	public void sendMessage(Player player, 
							@Required(name = "消息") String message, // 必要参数，usage是参数的用法
							@Optional(value = "false", name = "前缀") boolean prefix) { // 可选参数，value是默认值，usage是参数的用法
		player.sendMessage(prefix?player.getDisplayName()+":"+message:message);
	}
}
