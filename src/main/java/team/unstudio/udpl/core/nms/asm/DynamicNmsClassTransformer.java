package team.unstudio.udpl.core.nms.asm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.google.common.collect.Maps;

import team.unstudio.udpl.nms.mapping.MemberMapping;
import team.unstudio.udpl.util.asm.*;
import team.unstudio.udpl.util.asm.commons.ClassRemapper;
import team.unstudio.udpl.util.asm.commons.Remapper;

public class DynamicNmsClassTransformer implements Opcodes{
	
	private final String targetNmsVersion;
	private final String targetMinecraftVersion;
	private final String targetNmsPackage;
	private final String targetObcPackage;
	private final Map<String,MemberMapping> loadedMapping = Maps.newHashMap();
	
	public DynamicNmsClassTransformer(String targetNmsVersion, String targetMinecraftVersion) throws IOException {
		this.targetNmsVersion = targetNmsVersion;
		this.targetNmsPackage = "net/minecraft/server/"+targetNmsVersion;
		this.targetObcPackage = "org/bukkit/craftbukkit/"+targetNmsVersion;
		this.targetMinecraftVersion = targetMinecraftVersion;
	}

	public final String getTargetNmsVersion() {
		return targetNmsVersion;
	}
	
	public final String getTargetMinecraftVersion() {
		return targetMinecraftVersion;
	}
	
	public final MemberMapping getMemberMapping(String version) throws IOException{
		if(!loadedMapping.containsKey(version))
			loadedMapping.put(version, new MemberMapping(version));
		return loadedMapping.get(version);
	}
	
	public byte[] transform(InputStream inputStream, String sourceNmsVersion, String sourceMinecraftVersion) throws IOException{
		ClassReader classReader = new ClassReader(inputStream);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassRemapper classVisitor = new ClassRemapper(classWriter,new NmsRemapper(sourceNmsVersion,sourceMinecraftVersion));
		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
		return classWriter.toByteArray();
	}
	
	public byte[] transform(byte[] bytes, String sourceNmsVersion, String sourceMinecraftVersion) throws IOException{
		ClassReader classReader = new ClassReader(bytes);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassRemapper classVisitor = new ClassRemapper(classWriter,new NmsRemapper(sourceNmsVersion,sourceMinecraftVersion));
		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
		return classWriter.toByteArray();
	}

	private class NmsRemapper extends Remapper{
		
		private final String sourceNmsPackage;
		private final String sourceObcPackage;
		private final MemberMapping sourceMemberMapping;
		private final MemberMapping targetMemberMapping;
		
		public NmsRemapper(String sourceNmsVersion, String sourceMinecraftVersion) throws IOException {
			this.sourceNmsPackage = "net/minecraft/server/"+sourceNmsVersion;
			this.sourceObcPackage = "org/bukkit/craftbukkit/"+sourceNmsVersion;
			this.sourceMemberMapping = getMemberMapping(sourceMinecraftVersion);
			this.targetMemberMapping = getMemberMapping(getTargetMinecraftVersion());
		}
		
		@Override
		public String map(String typeName) {
			return transformPackage(typeName);
		}
		
		@Override
		public String mapMethodName(String owner, String name, String desc) {
			if(owner.startsWith(sourceNmsPackage)){
				String obf = sourceMemberMapping.getObf(getClassSimpleName(owner), name, getSimpleDesc(desc));
				name = targetMemberMapping.getDeobf(getClassSimpleName(owner), obf, getSimpleDesc(desc));
			}
			return transformPackage(name);
		}
		
		@Override
		public String mapInvokeDynamicMethodName(String name, String desc) {
			return transformPackage(name);
		}
		
		@Override
		public String mapFieldName(String owner, String name, String desc) {
			if(owner.startsWith(sourceNmsPackage)){
				String obf = sourceMemberMapping.getObf(getClassSimpleName(owner), name, "");
				name = targetMemberMapping.getDeobf(getClassSimpleName(owner), obf, "");
			}
			return transformPackage(name);
		}
		
		private String transformPackage(String value){
			return value.replaceAll(sourceNmsPackage, targetNmsPackage).replaceAll(sourceObcPackage, targetObcPackage);
		}
		
		private String getClassSimpleName(String name){
			return name.substring(name.lastIndexOf("/")+1);
		}
		
		private String getSimpleDesc(String desc){
	        if ("()V".equals(desc)) {
	            return desc;
	        }

	        Type[] args = Type.getArgumentTypes(desc);
	        StringBuilder sb = new StringBuilder("(");
	        for (int i = 0; i < args.length; i++) {
	            sb.append(_getSimpleDesc(args[i].getDescriptor()));
	        }
	        Type returnType = Type.getReturnType(desc);
	        if (returnType == Type.VOID_TYPE) {
	            sb.append(")V");
	            return sb.toString();
	        }
	        sb.append(')').append(_getSimpleDesc(returnType.getDescriptor()));
	        return sb.toString();
		}
		
		private String _getSimpleDesc(String desc){
			 Type t = Type.getType(desc);
		        switch (t.getSort()) {
		        case Type.ARRAY:
		            String s = _getSimpleDesc(t.getElementType().getDescriptor());
		            for (int i = 0; i < t.getDimensions(); ++i) {
		                s = '[' + s;
		            }
		            return s;
		        case Type.OBJECT:
		            String newType = getClassSimpleName(transformPackage(t.getInternalName()));
		            if (newType != null) {
		                return 'L' + newType + ';';
		            }
		        }
		        return desc;
		}
	}
}
