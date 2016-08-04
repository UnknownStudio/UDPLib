package team.unstudio.udpc.core;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpc.api.command.CommandHandler;
import team.unstudio.udpc.api.command.CommandManager;

public class UDPCore extends JavaPlugin{
	
	public static final String NAME = "UDPCore";
	public static final String VERSION = "1.0.0-SANPSHOT";
	
	private static final File PLUGIN_PATH = new File("plugins");
	
	public static UDPCore INSTANCE;
	public static boolean debug;
	
	@Override
	public void onLoad() {
		INSTANCE = this;
	}
	
	@Override
	public void onEnable() {
		new CommandManager("pm", this).addHandler(new CommandHandler() {
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin((String)args[0]));
				sender.sendMessage("[PluginManager]卸载插件成功: "+args[0]);
				return true;
			}
		}.setSub("disable").setPermission("udpc.pm.disable").setParameterTypes(String.class).setUsage("<Plugin>"))
		.addHandler(new CommandHandler() {
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				String file = (String) args[0];
				if(!file.endsWith(".jar"))file=file+".jar";
					try {
						Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File(PLUGIN_PATH, file)));
						sender.sendMessage("[PluginManager]加载插件成功: "+file);
					} catch (Exception e) {
						sender.sendMessage("[PluginManager]加载插件失败: "+file);
					}
				return true;
			}
		}.setSub("enable").setPermission("udpc.pm.enable").setParameterTypes(String.class).setUsage("<Plugin>"))
		.addHandler(new CommandHandler() {
			@Override
			public boolean onCommand(CommandSender sender, Object[] args) {
				Bukkit.getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin((String)args[0]));
				sender.sendMessage("[PluginManager]卸载插件成功: "+args[0]);
				return true;
			}
		}.setSub("plugins").setPermission("udpc.pm.plugins")).registerCommand();
	}
	
	@Override
	public void onDisable() {
	}
	
	public static void debug(String arg){
		if(debug)INSTANCE.getLogger().info(arg);
	}
}
