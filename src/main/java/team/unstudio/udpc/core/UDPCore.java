package team.unstudio.udpc.core;

import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpc.api.command.CommandManager;
import team.unstudio.udpc.api.command.TabCompleteHelper;
import team.unstudio.udpc.api.pluginmanager.PluginManager;

public class UDPCore extends JavaPlugin{
	
	public static final String NAME = "UDPCore";
	public static final String VERSION = "1.0.0-SANPSHOT";
	
	public static UDPCore INSTANCE;
	public static boolean debug;
	
	@Override
	public void onLoad() {
		INSTANCE = this;
	}
	
	@Override
	public void onEnable() {
		new CommandManager("pm", this).addCommandHandler(new PluginManager()).registerCommand();
		getServer().getPluginManager().registerEvents(new TabCompleteHelper(null), this);
	}
	
	@Override
	public void onDisable() {
	}
	
	public static void debug(String arg){
		if(debug)INSTANCE.getLogger().info(arg);
	}
}
