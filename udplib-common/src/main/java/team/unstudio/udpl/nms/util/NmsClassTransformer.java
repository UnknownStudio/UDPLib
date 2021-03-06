package team.unstudio.udpl.nms.util;

import com.google.common.collect.Maps;
import team.unstudio.udpl.exception.MemberMappingException;
import team.unstudio.udpl.nms.mapping.MemberMapping;
import team.unstudio.udpl.util.asm.ClassReader;
import team.unstudio.udpl.util.asm.ClassWriter;
import team.unstudio.udpl.util.asm.Opcodes;
import team.unstudio.udpl.util.asm.Type;
import team.unstudio.udpl.util.asm.commons.ClassRemapper;
import team.unstudio.udpl.util.asm.commons.Remapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class NmsClassTransformer implements Opcodes{

	private final String targetNmsVersion;
	private final String targetMinecraftVersion;
	private final String targetNmsPackage;
	private final String targetObcPackage;
	private final Map<String,MemberMapping> loadedMapping = Maps.newHashMap();

	public NmsClassTransformer(String targetNmsVersion, String targetMinecraftVersion){
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

	public final MemberMapping getMemberMapping(String version) throws MemberMappingException {
		if(!loadedMapping.containsKey(version))
			loadedMapping.put(version, MemberMapping.fromClassLoader(version));
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
            for (Type arg : args) {
                sb.append(_getSimpleDesc(arg.getDescriptor()));
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
		            StringBuilder s = new StringBuilder(_getSimpleDesc(t.getElementType().getDescriptor()));
		            for (int i = 0; i < t.getDimensions(); ++i) {
		                s.insert(0, '[');
		            }
		            return s.toString();
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
