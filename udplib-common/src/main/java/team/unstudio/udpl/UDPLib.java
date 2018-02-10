package team.unstudio.udpl;

import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import team.unstudio.udpl.core.UDPLI18n;
import team.unstudio.udpl.i18n.I18n;

public interface UDPLib {

	static I18n getI18n(){
		return UDPLI18n.I18N;
	}
	
	static boolean isDebug(){
		return team.unstudio.udpl.core.UDPLib.isDebug();
	}
	
	static void debug(Throwable e) {
		if(isDebug())
			getLogger().debug(e);
	}
	
	static Logger getLogger() {
		return null;
	}
	
	static JavaPlugin getPlugin() {
		return null;
	}
}
