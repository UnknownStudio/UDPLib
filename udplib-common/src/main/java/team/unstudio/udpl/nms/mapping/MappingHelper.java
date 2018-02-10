package team.unstudio.udpl.nms.mapping;

import team.unstudio.udpl.UDPLib;
import team.unstudio.udpl.util.ServerUtils;

import java.io.IOException;

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
			UDPLib.getLogger().info("Loaded mapping "+version);
		} catch (IOException e) {
			UDPLib.debug(e);
			memberMapping = null;
			UDPLib.getLogger().warn("Loaded mapping "+version+" failure.");
		}
	}
}
