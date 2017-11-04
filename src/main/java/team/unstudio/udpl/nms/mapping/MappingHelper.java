package team.unstudio.udpl.nms.mapping;

import java.io.IOException;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.util.ServerUtils;

public final class MappingHelper {
	
	private MappingHelper(){}
	
	private static MemberMapping memberMapping;
	
	public static MemberMapping getMemberMapping() {
		return memberMapping;
	}
	
	public static void loadMapping(){
		loadMapping(ServerUtils.getMinecraftVersion());
	}
	
	private static void loadMapping(String version){
		try {
			memberMapping = new MemberMapping(version);
			UDPLib.getLog().info("Loaded mapping "+version);
		} catch (IOException e) {
			UDPLib.debug(e);
			memberMapping = null;
			UDPLib.getLog().warn("Loaded mapping "+version+" failure.");
		}
	}
}
