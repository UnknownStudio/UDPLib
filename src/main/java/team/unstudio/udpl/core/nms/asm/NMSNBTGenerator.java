package team.unstudio.udpl.core.nms.asm;

import team.unstudio.udpl.api.util.asm.*;

public class NMSNBTGenerator implements Opcodes {

	public static byte[] generateNMSNBT() {
		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;
		cw.visit(52, ACC_PUBLIC + ACC_SUPER, "team/unstudio/udpl/core/nms/asm/NMSNBT", null, "java/lang/Object",
				new String[] { "team/unstudio/udpl/api/nms/NMSNBT" });

		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$SWITCH_TABLE$team$unstudio$udpl$api$nbt$NBTBaseType", "[I", null, null);
			fv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(16, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			mv.visitInsn(RETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toMap", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagCompound;",
					null, new String[] { "java/lang/Exception" });
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(20, l0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagCompound");
			mv.visitVarInsn(ASTORE, 2);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(21, l1);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagCompound");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagCompound", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 3);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(22, l2);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagCompound", "c", "()Ljava/util/Set;",
					false);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
			mv.visitVarInsn(ASTORE, 5);
			Label l3 = new Label();
			mv.visitJumpInsn(GOTO, l3);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitFrame(Opcodes.F_FULL, 6,
					new Object[] { "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "java/lang/Object",
							"net/minecraft/server/v1_11_R1/NBTTagCompound", "team/unstudio/udpl/api/nbt/NBTTagCompound",
							Opcodes.TOP, "java/util/Iterator" },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "java/lang/String");
			mv.visitVarInsn(ASTORE, 4);
			Label l5 = new Label();
			mv.visitLabel(l5);
			mv.visitLineNumber(23, l5);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagCompound", "get",
					"(Ljava/lang/String;)Lnet/minecraft/server/v1_11_R1/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toNBTBase",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagCompound", "set",
					"(Ljava/lang/String;Lteam/unstudio/udpl/api/nbt/NBTBase;)Lteam/unstudio/udpl/api/nbt/NBTTagCompound;",
					false);
			mv.visitInsn(POP);
			mv.visitLabel(l3);
			mv.visitLineNumber(22, l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l4);
			Label l6 = new Label();
			mv.visitLabel(l6);
			mv.visitLineNumber(24, l6);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ARETURN);
			Label l7 = new Label();
			mv.visitLabel(l7);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l7, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l7, 1);
			mv.visitLocalVariable("oldNbt", "Lnet/minecraft/server/v1_11_R1/NBTTagCompound;", null, l1, l7, 2);
			mv.visitLocalVariable("newNbt", "Lteam/unstudio/udpl/api/nbt/NBTTagCompound;", null, l2, l7, 3);
			mv.visitLocalVariable("key", "Ljava/lang/String;", null, l5, l3, 4);
			mv.visitMaxs(5, 6);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toList", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagList;",
					null, new String[] { "java/lang/Exception" });
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(29, l0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagList");
			mv.visitVarInsn(ASTORE, 2);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLineNumber(30, l1);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagList");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagList", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 3);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(31, l2);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 4);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagList", "size", "()I", false);
			mv.visitVarInsn(ISTORE, 5);
			Label l4 = new Label();
			mv.visitLabel(l4);
			Label l5 = new Label();
			mv.visitJumpInsn(GOTO, l5);
			Label l6 = new Label();
			mv.visitLabel(l6);
			mv.visitLineNumber(32, l6);
			mv.visitFrame(Opcodes.F_FULL, 6,
					new Object[] { "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "java/lang/Object",
							"net/minecraft/server/v1_11_R1/NBTTagList", "team/unstudio/udpl/api/nbt/NBTTagList",
							Opcodes.INTEGER, Opcodes.INTEGER },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagList", "h",
					"(I)Lnet/minecraft/server/v1_11_R1/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toNBTBase",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagList", "add",
					"(Lteam/unstudio/udpl/api/nbt/NBTBase;)Lteam/unstudio/udpl/api/nbt/NBTTagList;", false);
			mv.visitInsn(POP);
			Label l7 = new Label();
			mv.visitLabel(l7);
			mv.visitLineNumber(31, l7);
			mv.visitIincInsn(4, 1);
			mv.visitLabel(l5);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitJumpInsn(IF_ICMPLT, l6);
			Label l8 = new Label();
			mv.visitLabel(l8);
			mv.visitLineNumber(33, l8);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ARETURN);
			Label l9 = new Label();
			mv.visitLabel(l9);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l9, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l9, 1);
			mv.visitLocalVariable("oldNbt", "Lnet/minecraft/server/v1_11_R1/NBTTagList;", null, l1, l9, 2);
			mv.visitLocalVariable("newNbt", "Lteam/unstudio/udpl/api/nbt/NBTTagList;", null, l2, l9, 3);
			mv.visitLocalVariable("i", "I", null, l3, l8, 4);
			mv.visitLocalVariable("size", "I", null, l4, l8, 5);
			mv.visitMaxs(4, 6);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toByte", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagByte;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(38, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagByte");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagByte");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagByte", "g", "()B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagByte", "<init>", "(B)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toShort", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagShort;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(43, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagShort");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagShort");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagShort", "f", "()S", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagShort", "<init>", "(S)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toInt", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagInt;", null,
					null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(48, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagInt");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagInt");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagInt", "e", "()I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagInt", "<init>", "(I)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toLong", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagLong;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(53, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagLong");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagLong");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagLong", "d", "()J", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagLong", "<init>", "(J)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(4, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toFloat", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagFloat;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(58, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagFloat");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagFloat");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagFloat", "i", "()F", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagFloat", "<init>", "(F)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toDouble", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagDouble;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(63, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagDouble");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagDouble");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagDouble", "asDouble", "()D", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagDouble", "<init>", "(D)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(4, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toString", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagString;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(68, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagString");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagString");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagString", "c_",
					"()Ljava/lang/String;", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagString", "<init>",
					"(Ljava/lang/String;)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toByteArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagByteArray;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(73, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagByteArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagByteArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagByteArray", "c", "()[B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagByteArray", "<init>", "([B)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toIntArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagIntArray;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(78, l0);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/api/nbt/NBTTagIntArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTTagIntArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagIntArray", "d", "()[I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/api/nbt/NBTTagIntArray", "<init>", "([I)V", false);
			mv.visitInsn(ARETURN);
			Label l1 = new Label();
			mv.visitLabel(l1);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l1, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l1, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toNBTBase", "(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTBase;",
					null, new String[] { "java/lang/Exception" });
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(83, l0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/v1_11_R1/NBTBase");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTBase", "getTypeId", "()B", false);
			Label l1 = new Label();
			Label l2 = new Label();
			Label l3 = new Label();
			Label l4 = new Label();
			Label l5 = new Label();
			Label l6 = new Label();
			Label l7 = new Label();
			Label l8 = new Label();
			Label l9 = new Label();
			Label l10 = new Label();
			Label l11 = new Label();
			Label l12 = new Label();
			mv.visitTableSwitchInsn(1, 11, l12, new Label[] { l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11 });
			mv.visitLabel(l1);
			mv.visitLineNumber(85, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toByte",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagByte;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l2);
			mv.visitLineNumber(87, l2);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toShort",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagShort;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l3);
			mv.visitLineNumber(89, l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toInt",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagInt;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l4);
			mv.visitLineNumber(91, l4);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toLong",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagLong;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l5);
			mv.visitLineNumber(93, l5);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toFloat",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagFloat;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(95, l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toDouble",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagDouble;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l7);
			mv.visitLineNumber(97, l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toByteArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagByteArray;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l11);
			mv.visitLineNumber(99, l11);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toIntArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagIntArray;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l8);
			mv.visitLineNumber(101, l8);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toString",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagString;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l9);
			mv.visitLineNumber(103, l9);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toList",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagList;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l10);
			mv.visitLineNumber(105, l10);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toMap",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/api/nbt/NBTTagCompound;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l12);
			mv.visitLineNumber(107, l12);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			Label l13 = new Label();
			mv.visitLabel(l13);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l13, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l13, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toNBT",
					"(Lteam/unstudio/udpl/api/nbt/NBTBase;)Lnet/minecraft/server/v1_11_R1/NBTBase;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(112, l0);
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT",
					"$SWITCH_TABLE$team$unstudio$udpl$api$nbt$NBTBaseType", "()[I", false);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBase", "getType",
					"()Lteam/unstudio/udpl/api/nbt/NBTBaseType;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(IALOAD);
			Label l1 = new Label();
			Label l2 = new Label();
			Label l3 = new Label();
			Label l4 = new Label();
			Label l5 = new Label();
			Label l6 = new Label();
			Label l7 = new Label();
			Label l8 = new Label();
			Label l9 = new Label();
			Label l10 = new Label();
			Label l11 = new Label();
			Label l12 = new Label();
			Label l13 = new Label();
			mv.visitTableSwitchInsn(1, 12, l13, new Label[] { l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12 });
			mv.visitLabel(l2);
			mv.visitLineNumber(114, l2);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagByte");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagByte");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagByte", "getValue", "()B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagByte", "<init>", "(B)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l11);
			mv.visitLineNumber(116, l11);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagShort");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagShort");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagShort", "getValue", "()S", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagShort", "<init>", "(S)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l5);
			mv.visitLineNumber(118, l5);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagInt");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagInt");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagInt", "getValue", "()I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagInt", "<init>", "(I)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l10);
			mv.visitLineNumber(120, l10);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagLong");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagLong");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagLong", "getValue", "()J", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagLong", "<init>", "(J)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l4);
			mv.visitLineNumber(122, l4);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagFloat");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagFloat");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagFloat", "getValue", "()F", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagFloat", "<init>", "(F)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l3);
			mv.visitLineNumber(124, l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagDouble");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagDouble");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagDouble", "getValue", "()D", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagDouble", "<init>", "(D)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l7);
			mv.visitLineNumber(126, l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagByteArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagByteArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagByteArray", "getValue", "()[B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagByteArray", "<init>", "([B)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(128, l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagIntArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagIntArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagIntArray", "getValue", "()[I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagIntArray", "<init>", "([I)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(130, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagString");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagString");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagString", "getValue",
					"()Ljava/lang/String;", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagString", "<init>",
					"(Ljava/lang/String;)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l9);
			mv.visitLineNumber(132, l9);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagList");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagList", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 2);
			Label l14 = new Label();
			mv.visitLabel(l14);
			mv.visitLineNumber(133, l14);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagList");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagList", "getList", "()Ljava/util/List;",
					false);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/List", "iterator", "()Ljava/util/Iterator;", true);
			mv.visitVarInsn(ASTORE, 4);
			Label l15 = new Label();
			mv.visitJumpInsn(GOTO, l15);
			Label l16 = new Label();
			mv.visitLabel(l16);
			mv.visitFrame(Opcodes.F_FULL, 5,
					new Object[] { "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "team/unstudio/udpl/api/nbt/NBTBase",
							"net/minecraft/server/v1_11_R1/NBTTagList", Opcodes.TOP, "java/util/Iterator" },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTBase");
			mv.visitVarInsn(ASTORE, 3);
			Label l17 = new Label();
			mv.visitLabel(l17);
			mv.visitLineNumber(134, l17);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toNBT",
					"(Lteam/unstudio/udpl/api/nbt/NBTBase;)Lnet/minecraft/server/v1_11_R1/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagList", "add",
					"(Lnet/minecraft/server/v1_11_R1/NBTBase;)V", false);
			mv.visitLabel(l15);
			mv.visitLineNumber(133, l15);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l16);
			Label l18 = new Label();
			mv.visitLabel(l18);
			mv.visitLineNumber(135, l18);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l8);
			mv.visitLineNumber(137, l8);
			mv.visitFrame(Opcodes.F_FULL, 2, new Object[] { "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT",
					"team/unstudio/udpl/api/nbt/NBTBase" }, 0, new Object[] {});
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/api/nbt/NBTTagCompound");
			mv.visitVarInsn(ASTORE, 3);
			Label l19 = new Label();
			mv.visitLabel(l19);
			mv.visitLineNumber(138, l19);
			mv.visitTypeInsn(NEW, "net/minecraft/server/v1_11_R1/NBTTagCompound");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/v1_11_R1/NBTTagCompound", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 4);
			Label l20 = new Label();
			mv.visitLabel(l20);
			mv.visitLineNumber(139, l20);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagCompound", "keySet",
					"()Ljava/util/Set;", false);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
			mv.visitVarInsn(ASTORE, 6);
			Label l21 = new Label();
			mv.visitJumpInsn(GOTO, l21);
			Label l22 = new Label();
			mv.visitLabel(l22);
			mv.visitFrame(Opcodes.F_FULL, 7,
					new Object[] { "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "team/unstudio/udpl/api/nbt/NBTBase",
							Opcodes.TOP, "team/unstudio/udpl/api/nbt/NBTTagCompound",
							"net/minecraft/server/v1_11_R1/NBTTagCompound", Opcodes.TOP, "java/util/Iterator" },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "java/lang/String");
			mv.visitVarInsn(ASTORE, 5);
			Label l23 = new Label();
			mv.visitLabel(l23);
			mv.visitLineNumber(140, l23);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTTagCompound", "get",
					"(Ljava/lang/String;)Lteam/unstudio/udpl/api/nbt/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toNBT",
					"(Lteam/unstudio/udpl/api/nbt/NBTBase;)Lnet/minecraft/server/v1_11_R1/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/v1_11_R1/NBTTagCompound", "set",
					"(Ljava/lang/String;Lnet/minecraft/server/v1_11_R1/NBTBase;)V", false);
			mv.visitLabel(l21);
			mv.visitLineNumber(139, l21);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l22);
			Label l24 = new Label();
			mv.visitLabel(l24);
			mv.visitLineNumber(141, l24);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l12);
			mv.visitLineNumber(143, l12);
			mv.visitFrame(Opcodes.F_FULL, 2, new Object[] { "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT",
					"team/unstudio/udpl/api/nbt/NBTBase" }, 0, new Object[] {});
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l13);
			mv.visitLineNumber(145, l13);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			Label l25 = new Label();
			mv.visitLabel(l25);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/v1_11_R1/NMSNBT;", null, l0, l25, 0);
			mv.visitLocalVariable("nbt", "Lteam/unstudio/udpl/api/nbt/NBTBase;", null, l0, l25, 1);
			mv.visitLocalVariable("nmsList", "Lnet/minecraft/server/v1_11_R1/NBTTagList;", null, l14, l8, 2);
			mv.visitLocalVariable("nbtBase", "Lteam/unstudio/udpl/api/nbt/NBTBase;", null, l17, l15, 3);
			mv.visitLocalVariable("oldNbtMap", "Lteam/unstudio/udpl/api/nbt/NBTTagCompound;", null, l19, l12, 3);
			mv.visitLocalVariable("nmsMap", "Lnet/minecraft/server/v1_11_R1/NBTTagCompound;", null, l20, l12, 4);
			mv.visitLocalVariable("key", "Ljava/lang/String;", null, l23, l21, 5);
			mv.visitMaxs(5, 7);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "toNBT",
					"(Lteam/unstudio/udpl/api/nbt/NBTBase;)Ljava/lang/Object;", null,
					new String[] { "java/lang/Exception" });
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(1, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT", "toNBT",
					"(Lteam/unstudio/udpl/api/nbt/NBTBase;)Lnet/minecraft/server/v1_11_R1/NBTBase;", false);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_STATIC + ACC_SYNTHETIC, "$SWITCH_TABLE$team$unstudio$udpl$api$nbt$NBTBaseType",
					"()[I", null, null);
			mv.visitCode();
			Label l0 = new Label();
			Label l1 = new Label();
			Label l2 = new Label();
			mv.visitTryCatchBlock(l0, l1, l2, "java/lang/NoSuchFieldError");
			Label l3 = new Label();
			Label l4 = new Label();
			Label l5 = new Label();
			mv.visitTryCatchBlock(l3, l4, l5, "java/lang/NoSuchFieldError");
			Label l6 = new Label();
			Label l7 = new Label();
			Label l8 = new Label();
			mv.visitTryCatchBlock(l6, l7, l8, "java/lang/NoSuchFieldError");
			Label l9 = new Label();
			Label l10 = new Label();
			Label l11 = new Label();
			mv.visitTryCatchBlock(l9, l10, l11, "java/lang/NoSuchFieldError");
			Label l12 = new Label();
			Label l13 = new Label();
			Label l14 = new Label();
			mv.visitTryCatchBlock(l12, l13, l14, "java/lang/NoSuchFieldError");
			Label l15 = new Label();
			Label l16 = new Label();
			Label l17 = new Label();
			mv.visitTryCatchBlock(l15, l16, l17, "java/lang/NoSuchFieldError");
			Label l18 = new Label();
			Label l19 = new Label();
			Label l20 = new Label();
			mv.visitTryCatchBlock(l18, l19, l20, "java/lang/NoSuchFieldError");
			Label l21 = new Label();
			Label l22 = new Label();
			Label l23 = new Label();
			mv.visitTryCatchBlock(l21, l22, l23, "java/lang/NoSuchFieldError");
			Label l24 = new Label();
			Label l25 = new Label();
			Label l26 = new Label();
			mv.visitTryCatchBlock(l24, l25, l26, "java/lang/NoSuchFieldError");
			Label l27 = new Label();
			Label l28 = new Label();
			Label l29 = new Label();
			mv.visitTryCatchBlock(l27, l28, l29, "java/lang/NoSuchFieldError");
			Label l30 = new Label();
			Label l31 = new Label();
			Label l32 = new Label();
			mv.visitTryCatchBlock(l30, l31, l32, "java/lang/NoSuchFieldError");
			Label l33 = new Label();
			Label l34 = new Label();
			Label l35 = new Label();
			mv.visitTryCatchBlock(l33, l34, l35, "java/lang/NoSuchFieldError");
			Label l36 = new Label();
			mv.visitLabel(l36);
			mv.visitLineNumber(16, l36);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT",
					"$SWITCH_TABLE$team$unstudio$udpl$api$nbt$NBTBaseType", "[I");
			mv.visitInsn(DUP);
			Label l37 = new Label();
			mv.visitJumpInsn(IFNULL, l37);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l37);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "[I" });
			mv.visitInsn(POP);
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "values",
					"()[Lteam/unstudio/udpl/api/nbt/NBTBaseType;", false);
			mv.visitInsn(ARRAYLENGTH);
			mv.visitIntInsn(NEWARRAY, T_INT);
			mv.visitVarInsn(ASTORE, 0);
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "BYTE",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(ICONST_2);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l1);
			mv.visitJumpInsn(GOTO, l3);
			mv.visitLabel(l2);
			mv.visitFrame(Opcodes.F_FULL, 1, new Object[] { "[I" }, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "BYTEARRAY",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 7);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l4);
			mv.visitJumpInsn(GOTO, l6);
			mv.visitLabel(l5);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "COMPOUND",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 8);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l7);
			mv.visitJumpInsn(GOTO, l9);
			mv.visitLabel(l8);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l9);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "DOUBLE",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(ICONST_3);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l10);
			mv.visitJumpInsn(GOTO, l12);
			mv.visitLabel(l11);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l12);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "END",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 12);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l13);
			mv.visitJumpInsn(GOTO, l15);
			mv.visitLabel(l14);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l15);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "FLOAT",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(ICONST_4);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l16);
			mv.visitJumpInsn(GOTO, l18);
			mv.visitLabel(l17);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l18);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "INTARRAY",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 6);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l19);
			mv.visitJumpInsn(GOTO, l21);
			mv.visitLabel(l20);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l21);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "INTEGER",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(ICONST_5);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l22);
			mv.visitJumpInsn(GOTO, l24);
			mv.visitLabel(l23);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l24);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "LIST",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 9);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l25);
			mv.visitJumpInsn(GOTO, l27);
			mv.visitLabel(l26);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l27);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "LONG",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 10);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l28);
			mv.visitJumpInsn(GOTO, l30);
			mv.visitLabel(l29);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l30);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "SHORT",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitIntInsn(BIPUSH, 11);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l31);
			mv.visitJumpInsn(GOTO, l33);
			mv.visitLabel(l32);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l33);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/api/nbt/NBTBaseType", "STRING",
					"Lteam/unstudio/udpl/api/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/api/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(ICONST_1);
			mv.visitInsn(IASTORE);
			mv.visitLabel(l34);
			Label l38 = new Label();
			mv.visitJumpInsn(GOTO, l38);
			mv.visitLabel(l35);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "java/lang/NoSuchFieldError" });
			mv.visitInsn(POP);
			mv.visitLabel(l38);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitInsn(DUP);
			mv.visitFieldInsn(PUTSTATIC, "team/unstudio/udpl/core/nms/v1_11_R1/NMSNBT",
					"$SWITCH_TABLE$team$unstudio$udpl$api$nbt$NBTBaseType", "[I");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(3, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
	
	
}
