package team.unstudio.udpl.api;

import team.unstudio.udpl.core.UDPLI18n;
import team.unstudio.udpl.i18n.I18n;

public interface UDPLib {

	static I18n getI18n(){
		return UDPLI18n.I18N;
	}
	
	static boolean isDebug(){
		return team.unstudio.udpl.core.UDPLib.isDebug();
	}
}
