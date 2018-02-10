package team.unstudio.udpl;

import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpl.annotation.Init;
import team.unstudio.udpl.i18n.I18n;

public final class UDPLib {
	
	@Init("core_i18n")
	private static I18n CORE_I18N;
	
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
			getLogger().debug(e);
	}
	
	public static Logger getLogger() {
		return null;
	}
	
	public static JavaPlugin getPlugin() {
		return null;
	}
}
