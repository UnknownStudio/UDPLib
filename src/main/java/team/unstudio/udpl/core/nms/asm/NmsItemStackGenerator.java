package team.unstudio.udpl.core.nms.asm;

import team.unstudio.udpl.util.asm.*;

public class NmsItemStackGenerator implements Opcodes {

	public static byte[] generate(String nmsVersion) {
		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;

		cw.visit(52, ACC_PUBLIC + ACC_SUPER, "team/unstudio/udpl/core/nms/asm/NmsItemStack", null,
				"java/lang/Object", new String[] { "team/unstudio/udpl/nms/inventory/NmsItemStack" });

		cw.visitSource(null, null);

		{
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "itemStack", "Lnet/minecraft/server/"+nmsVersion+"/ItemStack;", null,
					null);
			fv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Lorg/bukkit/inventory/ItemStack;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(13, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(14, l1);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKESTATIC, "org/bukkit/craftbukkit/"+nmsVersion+"/inventory/CraftItemStack", "asNMSCopy",
					"(Lorg/bukkit/inventory/ItemStack;)Lnet/minecraft/server/"+nmsVersion+"/ItemStack;", false);
			mv.visitFieldInsn(PUTFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(15, l2);
			mv.visitInsn(RETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l3, 0);
			mv.visitLocalVariable("itemStack", "Lorg/bukkit/inventory/ItemStack;", null, l0, l3, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "getItemStack", "()Lorg/bukkit/inventory/ItemStack;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(19, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			mv.visitMethodInsn(INVOKESTATIC, "org/bukkit/craftbukkit/"+nmsVersion+"/inventory/CraftItemStack", "asBukkitCopy",
					"(Lnet/minecraft/server/"+nmsVersion+"/ItemStack;)Lorg/bukkit/inventory/ItemStack;", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "getTag", "()Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(24, l0);
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/nms/NmsHelper", "getNBT",
					"()Lteam/unstudio/udpl/nms/nbt/NmsNBT;", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/"+nmsVersion+"/ItemStack", "getTag",
					"()Lnet/minecraft/server/"+nmsVersion+"/NBTTagCompound;", false);
			mv.visitMethodInsn(INVOKEINTERFACE, "team/unstudio/udpl/nms/nbt/NmsNBT", "toCompound",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", true);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l1, 0);
			mv.visitMaxs(2, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "setTag", "(Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(29, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/nms/NmsHelper", "getNBT",
					"()Lteam/unstudio/udpl/nms/nbt/NmsNBT;", false);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEINTERFACE, "team/unstudio/udpl/nms/nbt/NmsNBT", "toNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/"+nmsVersion+"/NBTTagCompound");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/"+nmsVersion+"/ItemStack", "setTag",
					"(Lnet/minecraft/server/"+nmsVersion+"/NBTTagCompound;)V", false);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(30, l1);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l2, 0);
			mv.visitLocalVariable("nbt", "Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, l0, l2, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "hasTag", "()Z", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(34, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/"+nmsVersion+"/ItemStack", "hasTag", "()Z", false);
			mv.visitInsn(IRETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "load", "(Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;)V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(39, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/nms/NmsHelper", "getNBT",
					"()Lteam/unstudio/udpl/nms/nbt/NmsNBT;", false);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEINTERFACE, "team/unstudio/udpl/nms/nbt/NmsNBT", "toNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/"+nmsVersion+"/NBTTagCompound");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/"+nmsVersion+"/ItemStack", "load",
					"(Lnet/minecraft/server/"+nmsVersion+"/NBTTagCompound;)V", false);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(40, l1);
			mv.visitInsn(RETURN);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l2, 0);
			mv.visitLocalVariable("nbt", "Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, l0, l2, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "save", "()Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(44, l0);
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/nms/NmsHelper", "getNBT",
					"()Lteam/unstudio/udpl/nms/nbt/NmsNBT;", false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETFIELD, "team/unstudio/udpl/core/nms/asm/NmsItemStack", "itemStack",
					"Lnet/minecraft/server/"+nmsVersion+"/ItemStack;");
			mv.visitTypeInsn(NEW, "net/minecraft/server/"+nmsVersion+"/NBTTagCompound");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/"+nmsVersion+"/NBTTagCompound", "<init>", "()V", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/"+nmsVersion+"/ItemStack", "save",
					"(Lnet/minecraft/server/"+nmsVersion+"/NBTTagCompound;)Lnet/minecraft/server/"+nmsVersion+"/NBTTagCompound;",
					false);
			mv.visitMethodInsn(INVOKEINTERFACE, "team/unstudio/udpl/nms/nbt/NmsNBT", "toCompound",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", true);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsItemStack;", null, l0, l1, 0);
			mv.visitMaxs(4, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}
