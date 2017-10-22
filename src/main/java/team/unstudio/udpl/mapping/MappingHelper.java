package team.unstudio.udpl.mapping;

import java.io.IOException;

import team.unstudio.udpl.core.UDPLib;
import team.unstudio.udpl.util.ServerUtils;

public final class MappingHelper {
	
	private MappingHelper(){}
	
	private static final boolean DEBUG = UDPLib.isDebug();
	
	private static MemberMapping memberMapping;
	
	public static void loadMapping(){
		loadMapping(ServerUtils.getMinecraftVersion());
	}
	
	public static void loadMapping(String version){
		try {
			memberMapping = new MemberMapping(version);
			UDPLib.getLog().info("Loaded mapping "+version);
		} catch (IOException e) {
			if(DEBUG)
				e.printStackTrace();
			memberMapping = null;
			UDPLib.getLog().warning("Loaded mapping "+version+" failure.");
		}
	}

	public static MemberMapping getMemberMapping() {
		return memberMapping;
	}
	
	public static String getMemberDeobf(String className,String obf,String def){
		if(memberMapping == null) 
			return def;
		
		return memberMapping.getDeobf(className,obf,def);
	}
	
	public static String getMemberDeobf(String className,String obf){
		if(memberMapping == null) 
			return "";
		
		return memberMapping.getDeobf(className,obf);
	}
	
	public static String getMemberObf(String className,String deobf,String def){
		if(memberMapping == null) 
			return def;
		
		return memberMapping.getObf(className,deobf,def);
	}
	
	public static String getMemberObf(String className,String deobf){
		if(memberMapping == null) 
			return "";
		
		return memberMapping.getObf(className,deobf);
	}
}
