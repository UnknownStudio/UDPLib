package team.unstudio.udpc.core;

import org.bukkit.plugin.java.JavaPlugin;

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
	}
	
	@Override
	public void onDisable() {
	}
	
	public static void debug(String arg){
		if(debug)INSTANCE.getLogger().info(arg);
	}
}
