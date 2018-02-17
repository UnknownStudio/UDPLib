package team.unstudio.udpl;

import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.i18n.I18n;

public final class UDPLib {
	
	@Init("core_i18n")
	private static I18n CORE_I18N;
	@Init("core_instance")
	private static JavaPlugin instance;
	@Init("core_logger")
	private static Logger logger;
	
	private static boolean DEBUG;

	public static I18n getI18n(){
		return CORE_I18N;
	}
	
	public static boolean isDebug(){
		return DEBUG;
	}
	
	public static void setDebug(boolean debug) {
		DEBUG = debug;
	}
	
	public static void debug(Throwable e) {
		if(isDebug())
			getLogger().warn(e.getMessage(), e);
	}
	
	public static Logger getLogger() {
		return logger;
	}
	
	public static JavaPlugin getPlugin() {
		return instance;
	}
}
