package team.unstudio.udpl.core;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import team.unstudio.udpl.command.anno.Command;
import team.unstudio.udpl.command.anno.Required;

public final class PluginManager {

	private static final File PLUGIN_PATH = new File("plugins");

	@Command(value = { "enable" }, permission = "udpl.pluginmanager.enable")
	public void enable(CommandSender sender, @Required String plugin) {
		if (!plugin.endsWith(".jar"))
			plugin = plugin + ".jar";
		try {
			Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File(PLUGIN_PATH, plugin)));
			sender.sendMessage("[PluginManager]加载插件成功: " + plugin);
		} catch (Exception e) {
			sender.sendMessage("[PluginManager]加载插件失败: " + plugin);
		}
	}

	@Command(value = { "disable" }, permission = "udpl.pluginmanager.disable")
	public void disable(CommandSender sender, @Required String plugin) {
		Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin(plugin));
		sender.sendMessage("[PluginManager]卸载插件成功: " + plugin);
	}

	@Command(value = { "plugins" }, permission = "udpl.pluginmanager.plugins")
	public void plugins(CommandSender sender, @Required String plugin) {
		StringBuilder b = new StringBuilder("[PluginManager]Plugins: ");
		for (Plugin p : Bukkit.getPluginManager().getPlugins())
			b.append(p.isEnabled() ? ChatColor.GREEN : ChatColor.RED).append(p.getName()).append(" ");
	}
}
