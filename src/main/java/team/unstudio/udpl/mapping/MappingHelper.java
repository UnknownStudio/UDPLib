package team.unstudio.udpl.mapping;

import java.io.IOException;

import org.bukkit.Bukkit;

import team.unstudio.udpl.core.UDPLib;

public enum MappingHelper {
	
	INSTANCE;
	
	private MappingHelper(){}
	
	private static final boolean DEBUG = UDPLib.isDebug();
	
	private MemberMapping memberMapping;
	
	public static void loadMapping(){
		INSTANCE.loadMapping(Bukkit.getBukkitVersion().substring(0, Bukkit.getBukkitVersion().indexOf("-")));
	}
	
	public void loadMapping(String version){
		try {
			memberMapping = new MemberMapping(MappingHelper.class.getResourceAsStream("/mappings/"+version+"/members.csrg"));
		} catch (IOException e) {
			if(DEBUG)
				e.printStackTrace();
			memberMapping = null;
		}
	}

	public MemberMapping getMemberMapping() {
		return memberMapping;
	}
	
	public String getMemberDeobf(String className,String obf){
		if(memberMapping == null) 
			return "";
		
		return memberMapping.getDeobf(className,obf);
	}
	
	public String getMemberObf(String className,String deobf){
		if(memberMapping == null) 
			return "";
		
		return memberMapping.getObf(className,deobf);
	}
}
