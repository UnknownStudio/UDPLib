package team.unstudio.udpl.core.nms.asm;

import java.io.IOException;
import java.io.InputStream;

import team.unstudio.udpl.util.asm.*;
import team.unstudio.udpl.util.asm.commons.ClassRemapper;
import team.unstudio.udpl.util.asm.commons.Remapper;

public class DynamicNmsClassTransformer implements Opcodes{
	
	private final String sourceNmsVersion;
	private final String targetNmsVersion;
	private final String sourceNmsPackage;
	private final String sourceObcPackage;
	private final String targetNmsPackage;
	private final String targetObcPackage;
	
	public DynamicNmsClassTransformer(String sourceNmsVersion,String targetNmsVersion) {
		this.sourceNmsVersion = sourceNmsVersion;
		this.sourceNmsPackage = "net/minecraft/server/"+sourceNmsVersion;
		this.sourceObcPackage = "org/bukkit/craftbukkit/"+sourceNmsVersion;
		this.targetNmsVersion = targetNmsVersion;
		this.targetNmsPackage = "net/minecraft/server/"+targetNmsVersion;
		this.targetObcPackage = "org/bukkit/craftbukkit/"+targetNmsVersion;
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
	
	private class NmsRemapper extends Remapper{
		@Override
		public String map(String typeName) {
			return transformPackage(typeName);
		}
		
		@Override
		public String mapMethodName(String owner, String name, String desc) {
			return transformPackage(name);
		}
		
		@Override
		public String mapInvokeDynamicMethodName(String name, String desc) {
			return transformPackage(name);
		}
		
		@Override
		public String mapFieldName(String owner, String name, String desc) {
			return transformPackage(name);
		}
	}
}
