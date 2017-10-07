package team.unstudio.udpl.mapping;

import java.io.IOException;

import org.bukkit.Bukkit;

public final class MappingHelper {
	
	public static final MappingHelper INSTANCE = new MappingHelper();
	
	static{
		INSTANCE.loadMapping();
	}
	
	public MappingHelper(){}
	
	private ClassMapping classMapping;
	private MemberMapping memberMapping;
	
	public void loadMapping(){
		loadMapping(Bukkit.getBukkitVersion().substring(0, Bukkit.getBukkitVersion().indexOf("-")));
	}
	
	public void loadMapping(String version){
		try {
			classMapping = new ClassMapping(MappingHelper.class.getResourceAsStream("/mappings/"+version+"/cl.csrg"));
		} catch (IOException e) {
			e.printStackTrace();
			classMapping = null;
		}
		try {
			memberMapping = new MemberMapping(MappingHelper.class.getResourceAsStream("/mappings/"+version+"/members.csrg"));
		} catch (IOException e) {
			e.printStackTrace();
			memberMapping = null;
		}
	}

	public ClassMapping getClassMapping() {
		return classMapping;
	}

	public MemberMapping getMemberMapping() {
		return memberMapping;
	}
	
	public String getClassDeobf(String obf){
		if(classMapping == null) 
			return "";
		
		return classMapping.getDeobf(obf);
	}
	
	public String getClassObf(String deobf){
		if(classMapping == null) 
			return "";
		
		return classMapping.getObf(deobf);
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
