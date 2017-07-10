package team.unstudio.udpl.api.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;

public final class MemberMapping {
	
	private final Map<String, Map<String,String>> obfToDeobf = Maps.newHashMap();
	private final Map<String, Map<String,String>> deobfToObf = Maps.newHashMap();
	
	public MemberMapping(InputStream inputStream) throws IOException {
		load(inputStream);
	}
	
	private void load(InputStream inputStream) throws IOException{
		for(String line:IOUtils.readLines(inputStream)){
			if(line.isEmpty()||line.startsWith("#"))
				continue;
				
			String[] args = line.split(" ",3);
			if(!obfToDeobf.containsKey(args[0]))
				obfToDeobf.put(args[0], Maps.newHashMap());
			obfToDeobf.get(args[0]).put(args[1], args[2]);
			
			if(!deobfToObf.containsKey(args[0]))
				deobfToObf.put(args[0], Maps.newHashMap());
			deobfToObf.get(args[0]).put(args[2], args[1]);
		}
	}
	
	public String getDeobf(String className,String obf){
		if(!obfToDeobf.containsKey(className))
			return "";
		return obfToDeobf.get(className).get(obf);
	}
	
	public String getObf(String className,String deobf){
		if(!deobfToObf.containsKey(className))
			return "";
		return deobfToObf.get(className).get(deobf);
	}

}
