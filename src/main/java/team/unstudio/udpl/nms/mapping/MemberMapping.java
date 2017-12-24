package team.unstudio.udpl.nms.mapping;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public final class MemberMapping {
	
	private final Map<String, Map<String,String>> obfToDeobf = Maps.newHashMap();
	private final Map<String, Map<String,String>> deobfToObf = Maps.newHashMap();
	
	public MemberMapping(String version) throws IOException {
		this(MemberMapping.class.getResourceAsStream("/mappings/"+version+"/members.csrg"));
	}
	
	public MemberMapping(InputStream inputStream) throws IOException {
		load(inputStream);
	}
	
	private void load(InputStream inputStream) throws IOException{
		load(IOUtils.readLines(inputStream));
	}
	
	private void load(List<String> list){
		for(String line:list){
			if(line.isEmpty()||line.startsWith("#"))
				continue;
				
			String[] args = line.split(" ");
			if(args.length == 4){ //Method
				if(!obfToDeobf.containsKey(args[0]))
					obfToDeobf.put(args[0], Maps.newHashMap());
				obfToDeobf.get(args[0]).put(args[1]+args[2], args[3]);
				
				if(!deobfToObf.containsKey(args[0]))
					deobfToObf.put(args[0], Maps.newHashMap());
				deobfToObf.get(args[0]).put(args[3]+args[2], args[1]);
			}else if(args.length == 3){ //Field
				if(!obfToDeobf.containsKey(args[0]))
					obfToDeobf.put(args[0], Maps.newHashMap());
				obfToDeobf.get(args[0]).put(args[1], args[2]);
				
				if(!deobfToObf.containsKey(args[0]))
					deobfToObf.put(args[0], Maps.newHashMap());
				deobfToObf.get(args[0]).put(args[2], args[1]);
			}
		}
	}
	
	public String getDeobf(String className, String obfName, String desc) {
		if (!containObf(className, obfName, desc))
			return obfName;
		
		return obfToDeobf.get(className).get(obfName.concat(desc));
	}

	public String getObf(String className, String deobfName, String desc) {
		if (!containDeobf(className, deobfName, desc))
			return deobfName;
		
		return deobfToObf.get(className).get(deobfName.concat(desc));
	}

	public boolean containObf(String className, String obfName, String desc) {
		if (!obfToDeobf.containsKey(className))
			return false;

		return obfToDeobf.get(className).containsKey(obfName.concat(desc));
	}

	public boolean containDeobf(String className, String deobfName, String desc) {
		if (!deobfToObf.containsKey(className))
			return false;

		return deobfToObf.get(className).containsKey(deobfName.concat(desc));
	}
}
