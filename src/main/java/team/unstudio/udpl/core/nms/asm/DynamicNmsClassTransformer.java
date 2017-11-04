package team.unstudio.udpl.core.nms.asm;

import java.io.IOException;
import java.io.InputStream;

import team.unstudio.udpl.nms.mapping.MemberMapping;
import team.unstudio.udpl.util.asm.*;
import team.unstudio.udpl.util.asm.commons.ClassRemapper;
import team.unstudio.udpl.util.asm.commons.Remapper;

public class DynamicNmsClassTransformer implements Opcodes{
	
	private final String sourceNmsVersion;
	private final String sourceMinecraftVersion;
	private final String targetNmsVersion;
	private final String targetMinecraftVersion;
	private final String sourceNmsPackage;
	private final String sourceObcPackage;
	private final String targetNmsPackage;
	private final String targetObcPackage;
	private final MemberMapping sourceMemberMapping;
	private final MemberMapping targetMemberMapping;
	
	public DynamicNmsClassTransformer(String sourceNmsVersion, String sourceMinecraftVersion, String targetNmsVersion, String targetMinecraftVersion) throws IOException {
		this.sourceNmsVersion = sourceNmsVersion;
		this.sourceNmsPackage = "net/minecraft/server/"+sourceNmsVersion;
		this.sourceObcPackage = "org/bukkit/craftbukkit/"+sourceNmsVersion;
		this.sourceMinecraftVersion = sourceMinecraftVersion;
		this.sourceMemberMapping = new MemberMapping(sourceMinecraftVersion);
		this.targetNmsVersion = targetNmsVersion;
		this.targetNmsPackage = "net/minecraft/server/"+targetNmsVersion;
		this.targetObcPackage = "org/bukkit/craftbukkit/"+targetNmsVersion;
		this.targetMinecraftVersion = targetMinecraftVersion;
		this.targetMemberMapping = new MemberMapping(targetMinecraftVersion);
	}
	
	public final String getSourceNmsVersion() {
		return sourceNmsVersion;
	}

	public final String getTargetNmsVersion() {
		return targetNmsVersion;
	}
	
	public byte[] transform(InputStream inputStream) throws IOException{
		ClassReader classReader = new ClassReader(inputStream);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassRemapper classVisitor = new ClassRemapper(classWriter,new NmsRemapper());
		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
		return classWriter.toByteArray();
	}
	
	public byte[] transform(byte[] bytes){
		ClassReader classReader = new ClassReader(bytes);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		ClassRemapper classVisitor = new ClassRemapper(classWriter,new NmsRemapper());
		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
		return classWriter.toByteArray();
	}
	
	protected String transformPackage(String value){
		return value.replaceAll(sourceNmsPackage, targetNmsPackage).replaceAll(sourceObcPackage, targetObcPackage);
	}
	
	public String getSourceMinecraftVersion() {
		return sourceMinecraftVersion;
	}

	public String getTargetMinecraftVersion() {
		return targetMinecraftVersion;
	}

	private class NmsRemapper extends Remapper{
		@Override
		public String map(String typeName) {
			return transformPackage(typeName);
		}
		
		@Override
		public String mapMethodName(String owner, String name, String desc) {
			if(owner.startsWith(sourceNmsPackage)){
				String obf = sourceMemberMapping.getObf(getClassSimpleName(owner), name+getSimpleDesc(desc), name);
				name = targetMemberMapping.getDeobf(getClassSimpleName(owner), obf+getSimpleDesc(desc), obf);
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
				String obf = sourceMemberMapping.getObf(getClassSimpleName(owner), name, name);
				name = targetMemberMapping.getDeobf(getClassSimpleName(owner), obf, obf);
			}
			return transformPackage(name);
		}
	}
	
	protected String getClassSimpleName(String name){
		return name.substring(name.lastIndexOf("/")+1);
	}
	
	protected String getSimpleDesc(String desc){
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
