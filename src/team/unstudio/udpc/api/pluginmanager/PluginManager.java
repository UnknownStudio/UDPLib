package team.unstudio.udpc.api.pluginmanager;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.UnknownDependencyException;

import team.unstudio.udpc.api.command.Command;
import team.unstudio.udpc.api.command.CommandHandler;

public class PluginManager implements CommandHandler{
	
	private final org.bukkit.plugin.PluginManager pluginManager;
	private static final File PLUGIN_PATH = new File("plugins");
	
	public PluginManager() {
		pluginManager = Bukkit.getPluginManager();
	}
	
	@Command(value = "disable",permission = "udpc.plugin.disable",parameter = String.class)
	public void disable(CommandSender sender,Object[] args){
		pluginManager.disablePlugin(pluginManager.getPlugin((String)args[0]));
		sender.sendMessage("[PluginManager]卸载插件成功: "+args[0]);
	}
	
	@Command(value = "enable",permission = "udpc.plugin.enable",parameter = String.class)
	public void enable(CommandSender sender,Object[] args){
		String file = (String) args[0];
		if(!file.endsWith(".jar"))file=file+".jar";
			try {
				pluginManager.enablePlugin(pluginManager.loadPlugin(new File(PLUGIN_PATH, file)));
				sender.sendMessage("[PluginManager]加载插件成功: "+file);
			} catch (UnknownDependencyException e) {
				sender.sendMessage("[PluginManager]没有加载该插件的依赖: "+file);
			} catch (InvalidPluginException e) {
				sender.sendMessage("[PluginManager]插件已加载: "+file);
			} catch (InvalidDescriptionException e) {
				sender.sendMessage("[PluginManager]加载插件失败,无效的plugin.yml: "+file);
			}
	}
	
	@Command(value = "plugins",permission = "udpc.plugin.plugins")
	public void plugins(CommandSender sender,Object[] args){
		StringBuilder builder = new StringBuilder("[PluginManager] ");
		for(Plugin p:pluginManager.getPlugins()){
			builder.append(p.getName()+",");
		}
		sender.sendMessage(builder.substring(0,builder.length()-1));
	}
}
