package team.unstudio.udpl.api.mapping;

import java.io.IOException;

public enum MappingHelper {
	
	;
	
	private static ClassMapping classMapping;
	private static MemberMapping memberMapping;
	
	public static void loadMapping(String version){
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

	public static ClassMapping getClassMapping() {
		return classMapping;
	}

	public static MemberMapping getMemberMapping() {
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
