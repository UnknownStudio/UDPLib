package team.unstudio.udpl.core.nms.asm;

import team.unstudio.udpl.util.asm.*;

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
	
	public byte[] transform(byte[] bytes){
		ClassReader classReader = new ClassReader(bytes);
		ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		DynamicNmsClassVisitor classVisitor = new DynamicNmsClassVisitor(ASM5, classWriter);
		classReader.accept(classVisitor, ClassReader.SKIP_FRAMES);
		return classWriter.toByteArray();
	}
	
	protected String transformPackage(String value){
		return value.replaceAll(sourceNmsPackage, targetNmsPackage).replaceAll(sourceObcPackage, targetObcPackage);
	}
	
	private class DynamicNmsClassVisitor extends ClassVisitor{

		public DynamicNmsClassVisitor(int api, ClassVisitor cv) {
			super(api, cv);
		}
		
		@Override
		public void visit(int version, int access, String name, String signature, String superName,
				String[] interfaces) {
			if (interfaces != null)
				for (int i = 0, size = interfaces.length; i < size; i++)
					interfaces[i] = transformPackage(interfaces[i]);
			super.visit(version, access, name, signature, transformPackage(superName), interfaces);
		}
		
		@Override
		public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
			return new DynamicNmsFieldVisitor(api, super.visitField(access, name, transformPackage(desc), signature, value));
		}
		
		@Override
		public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
			return new DynamicNmsMethodVisitor(api, super.visitMethod(access, name, transformPackage(desc), signature, exceptions));
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitAnnotation(transformPackage(desc), visible));
		}
		
		@Override
		public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitTypeAnnotation(typeRef, typePath, transformPackage(desc), visible));
		}
	}
	
	private class DynamicNmsAnnotationVisitor extends AnnotationVisitor{

		public DynamicNmsAnnotationVisitor(int api, AnnotationVisitor av) {
			super(api, av);
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String name, String desc) {
			return new DynamicNmsAnnotationVisitor(api, super.visitAnnotation(name, transformPackage(desc)));
		}
		
		@Override
		public void visitEnum(String name, String desc, String value) {
			super.visitEnum(name, transformPackage(desc), value);
		}
	}
	
	private class DynamicNmsFieldVisitor extends FieldVisitor{

		public DynamicNmsFieldVisitor(int api, FieldVisitor fv) {
			super(api, fv);
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitAnnotation(transformPackage(desc), visible));
		}
		
		@Override
		public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitTypeAnnotation(typeRef, typePath, desc, visible));
		}
	}
	
	private class DynamicNmsMethodVisitor extends MethodVisitor{

		public DynamicNmsMethodVisitor(int api, MethodVisitor mv) {
			super(api, mv);
		}
		
		@Override
		public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
			super.visitMethodInsn(opcode, transformPackage(owner), name, desc, itf);
		}
		
		@Override
		public void visitFieldInsn(int opcode, String owner, String name, String desc) {
			super.visitFieldInsn(opcode, transformPackage(owner), name, transformPackage(desc));
		}
		
		@Override
		public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
			super.visitLocalVariable(name, transformPackage(desc), signature, start, end, index);
		}
		
		@Override
		public void visitTypeInsn(int opcode, String type) {
			super.visitTypeInsn(opcode, transformPackage(type));
		}
		
		@Override
		public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitAnnotation(transformPackage(desc), visible));
		}
		
		@Override
		public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitInsnAnnotation(typeRef, typePath, transformPackage(desc), visible));
		}
		
		@Override
		public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start,
				Label[] end, int[] index, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, transformPackage(desc), visible));
		}
		
		@Override
		public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
			super.visitInvokeDynamicInsn(name, transformPackage(desc), bsm, bsmArgs);
		}
		
		@Override
		public void visitMultiANewArrayInsn(String desc, int dims) {
			super.visitMultiANewArrayInsn(transformPackage(desc), dims);
		}
		
		@Override
		public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitParameterAnnotation(parameter, transformPackage(desc), visible));
		}
		
		@Override
		public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitTryCatchAnnotation(typeRef, typePath, transformPackage(desc), visible));
		}
		
		@Override
		public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
			return new DynamicNmsAnnotationVisitor(api, super.visitTypeAnnotation(typeRef, typePath, transformPackage(desc), visible));
		}
	}
}
