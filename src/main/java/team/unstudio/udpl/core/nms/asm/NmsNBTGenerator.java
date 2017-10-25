package team.unstudio.udpl.core.nms.asm;

import team.unstudio.udpl.mapping.MappingHelper;
import team.unstudio.udpl.util.asm.*;

public class NmsNBTGenerator implements Opcodes {

	public static byte[] generate(String nmsVersion) {
		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;

		cw.visit(52, ACC_PUBLIC + ACC_SUPER, "team/unstudio/udpl/core/nms/asm/NmsNBT", null, "java/lang/Object",
				new String[] { "team/unstudio/udpl/nms/nbt/NmsNBT" });

		cw.visitSource(null, null);

		{
			fv = cw.visitField(ACC_PRIVATE + ACC_STATIC + ACC_SYNTHETIC,
					"$SWITCH_TABLE$team$unstudio$udpl$nms$nbt$NBTBaseType", "[I", null, null);
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
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l1, 0);
			mv.visitMaxs(1, 1);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toCompound",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(20, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(21, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(22, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagCompound");
			mv.visitVarInsn(ASTORE, 2);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(23, l3);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagCompound");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagCompound", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 3);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLineNumber(24, l4);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagCompound", "c",
					"()Ljava/util/Set;", false);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
			mv.visitVarInsn(ASTORE, 5);
			Label l5 = new Label();
			mv.visitJumpInsn(GOTO, l5);
			Label l6 = new Label();
			mv.visitLabel(l6);
			mv.visitFrame(Opcodes.F_FULL, 6,
					new Object[] { "team/unstudio/udpl/core/nms/asm/NmsNBT", "java/lang/Object",
							"net/minecraft/server/" + nmsVersion + "/NBTTagCompound",
							"team/unstudio/udpl/nms/nbt/NBTTagCompound", Opcodes.TOP, "java/util/Iterator" },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "java/lang/String");
			mv.visitVarInsn(ASTORE, 4);
			Label l7 = new Label();
			mv.visitLabel(l7);
			mv.visitLineNumber(25, l7);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagCompound", "get",
					"(Ljava/lang/String;)Lnet/minecraft/server/" + nmsVersion + "/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toNBTBase",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagCompound", "set",
					"(Ljava/lang/String;Lteam/unstudio/udpl/nms/nbt/NBTBase;)Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;",
					false);
			mv.visitInsn(POP);
			mv.visitLabel(l5);
			mv.visitLineNumber(24, l5);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l6);
			Label l8 = new Label();
			mv.visitLabel(l8);
			mv.visitLineNumber(26, l8);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ARETURN);
			Label l9 = new Label();
			mv.visitLabel(l9);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l9, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l9, 1);
			mv.visitLocalVariable("oldNbt", "Lnet/minecraft/server/" + nmsVersion + "/NBTTagCompound;", null, l3, l9,
					2);
			mv.visitLocalVariable("newNbt", "Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, l4, l9, 3);
			mv.visitLocalVariable("key", "Ljava/lang/String;", null, l7, l5, 4);
			mv.visitMaxs(5, 6);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toList", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagList;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(31, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(32, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(33, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagList");
			mv.visitVarInsn(ASTORE, 2);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(34, l3);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagList");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagList", "<init>", "()V", false);
			mv.visitVarInsn(ASTORE, 3);
			Label l4 = new Label();
			mv.visitLabel(l4);
			mv.visitLineNumber(35, l4);
			mv.visitInsn(ICONST_0);
			mv.visitVarInsn(ISTORE, 4);
			Label l5 = new Label();
			mv.visitLabel(l5);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagList", "size", "()I",
					false);
			mv.visitVarInsn(ISTORE, 5);
			Label l6 = new Label();
			mv.visitLabel(l6);
			Label l7 = new Label();
			mv.visitJumpInsn(GOTO, l7);
			Label l8 = new Label();
			mv.visitLabel(l8);
			mv.visitLineNumber(36, l8);
			mv.visitFrame(Opcodes.F_FULL, 6,
					new Object[] { "team/unstudio/udpl/core/nms/asm/NmsNBT", "java/lang/Object",
							"net/minecraft/server/" + nmsVersion + "/NBTTagList",
							"team/unstudio/udpl/nms/nbt/NBTTagList", Opcodes.INTEGER, Opcodes.INTEGER },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagList", "h",
					"(I)Lnet/minecraft/server/" + nmsVersion + "/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toNBTBase",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagList", "add",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Lteam/unstudio/udpl/nms/nbt/NBTTagList;", false);
			mv.visitInsn(POP);
			Label l9 = new Label();
			mv.visitLabel(l9);
			mv.visitLineNumber(35, l9);
			mv.visitIincInsn(4, 1);
			mv.visitLabel(l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ILOAD, 4);
			mv.visitVarInsn(ILOAD, 5);
			mv.visitJumpInsn(IF_ICMPLT, l8);
			Label l10 = new Label();
			mv.visitLabel(l10);
			mv.visitLineNumber(37, l10);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitInsn(ARETURN);
			Label l11 = new Label();
			mv.visitLabel(l11);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l11, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l11, 1);
			mv.visitLocalVariable("oldNbt", "Lnet/minecraft/server/" + nmsVersion + "/NBTTagList;", null, l3, l11, 2);
			mv.visitLocalVariable("newNbt", "Lteam/unstudio/udpl/nms/nbt/NBTTagList;", null, l4, l11, 3);
			mv.visitLocalVariable("i", "I", null, l5, l10, 4);
			mv.visitLocalVariable("size", "I", null, l6, l10, 5);
			mv.visitMaxs(4, 6);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toByte", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagByte;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(42, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(43, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(44, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagByte");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagByte");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagByte", "g", "()B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagByte", "<init>", "(B)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toShort", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagShort;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(49, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(50, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(51, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagShort");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagShort");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagShort", "f", "()S", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagShort", "<init>", "(S)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toInt", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagInt;", null,
					null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(56, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(57, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(58, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagInt");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagInt");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagInt", "e", "()I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagInt", "<init>", "(I)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toLong", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagLong;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(63, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(64, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(65, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagLong");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagLong");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagLong", "d", "()J", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagLong", "<init>", "(J)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(4, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toFloat", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagFloat;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(70, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(71, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(72, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagFloat");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagFloat");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagFloat", "i", "()F", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagFloat", "<init>", "(F)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toDouble", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagDouble;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(77, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(78, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(79, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagDouble");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagDouble");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagDouble",
					MappingHelper.getMemberDeobf("NBTNumber", "h()D", "h"), "()D", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagDouble", "<init>", "(D)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(4, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toString", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagString;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(84, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(85, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(86, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagString");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagString");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagString", "c_",
					"()Ljava/lang/String;", false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagString", "<init>",
					"(Ljava/lang/String;)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toByteArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagByteArray;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(91, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(92, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(93, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagByteArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagByteArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagByteArray", "c", "()[B",
					false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagByteArray", "<init>", "([B)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toIntArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagIntArray;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(98, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(99, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(100, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "team/unstudio/udpl/nms/nbt/NBTTagIntArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTTagIntArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagIntArray", "d", "()[I",
					false);
			mv.visitMethodInsn(INVOKESPECIAL, "team/unstudio/udpl/nms/nbt/NBTTagIntArray", "<init>", "([I)V", false);
			mv.visitInsn(ARETURN);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l3, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l3, 1);
			mv.visitMaxs(3, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toNBTBase", "(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTBase;",
					null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(105, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(106, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(107, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "net/minecraft/server/" + nmsVersion + "/NBTBase");
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTBase", "getTypeId", "()B",
					false);
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
			Label l14 = new Label();
			mv.visitTableSwitchInsn(1, 11, l14, new Label[] { l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13 });
			mv.visitLabel(l3);
			mv.visitLineNumber(109, l3);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toByte",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagByte;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l4);
			mv.visitLineNumber(111, l4);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toShort",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagShort;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l5);
			mv.visitLineNumber(113, l5);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toInt",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagInt;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(115, l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toLong",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagLong;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l7);
			mv.visitLineNumber(117, l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toFloat",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagFloat;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l8);
			mv.visitLineNumber(119, l8);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toDouble",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagDouble;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l9);
			mv.visitLineNumber(121, l9);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toByteArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagByteArray;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l13);
			mv.visitLineNumber(123, l13);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toIntArray",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagIntArray;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l10);
			mv.visitLineNumber(125, l10);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toString",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagString;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l11);
			mv.visitLineNumber(127, l11);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toList",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagList;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l12);
			mv.visitLineNumber(129, l12);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toCompound",
					"(Ljava/lang/Object;)Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l14);
			mv.visitLineNumber(131, l14);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			Label l15 = new Label();
			mv.visitLabel(l15);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l15, 0);
			mv.visitLocalVariable("nbt", "Ljava/lang/Object;", null, l0, l15, 1);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "toNmsNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Lnet/minecraft/server/" + nmsVersion + "/NBTBase;", null,
					null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(136, l0);
			mv.visitVarInsn(ALOAD, 1);
			Label l1 = new Label();
			mv.visitJumpInsn(IFNONNULL, l1);
			Label l2 = new Label();
			mv.visitLabel(l2);
			mv.visitLineNumber(137, l2);
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l1);
			mv.visitLineNumber(138, l1);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/core/nms/asm/NmsNBT",
					"$SWITCH_TABLE$team$unstudio$udpl$nms$nbt$NBTBaseType", "()[I", false);
			Label l3 = new Label();
			mv.visitLabel(l3);
			mv.visitLineNumber(138, l3);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBase", "getType",
					"()Lteam/unstudio/udpl/nms/nbt/NBTBaseType;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
			mv.visitInsn(IALOAD);
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
			Label l14 = new Label();
			Label l15 = new Label();
			mv.visitTableSwitchInsn(1, 11, l15, new Label[] { l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14 });
			mv.visitLabel(l5);
			mv.visitLineNumber(140, l5);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagByte");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagByte");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagByte", "getValue", "()B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagByte", "<init>", "(B)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l14);
			mv.visitLineNumber(142, l14);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagShort");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagShort");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagShort", "getValue", "()S", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagShort", "<init>", "(S)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l8);
			mv.visitLineNumber(144, l8);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagInt");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagInt");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagInt", "getValue", "()I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagInt", "<init>", "(I)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l13);
			mv.visitLineNumber(146, l13);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagLong");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagLong");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagLong", "getValue", "()J", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagLong", "<init>", "(J)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l7);
			mv.visitLineNumber(148, l7);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagFloat");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagFloat");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagFloat", "getValue", "()F", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagFloat", "<init>", "(F)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l6);
			mv.visitLineNumber(150, l6);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagDouble");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagDouble");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagDouble", "getValue", "()D", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagDouble", "<init>", "(D)V",
					false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l10);
			mv.visitLineNumber(152, l10);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagByteArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagByteArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagByteArray", "getValue", "()[B", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagByteArray", "<init>",
					"([B)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l9);
			mv.visitLineNumber(154, l9);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagIntArray");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagIntArray");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagIntArray", "getValue", "()[I", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagIntArray", "<init>",
					"([I)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l4);
			mv.visitLineNumber(156, l4);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagString");
			mv.visitInsn(DUP);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagString");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagString", "getValue",
					"()Ljava/lang/String;", false);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagString", "<init>",
					"(Ljava/lang/String;)V", false);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l12);
			mv.visitLineNumber(158, l12);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagList");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagList", "<init>", "()V",
					false);
			mv.visitVarInsn(ASTORE, 2);
			Label l16 = new Label();
			mv.visitLabel(l16);
			mv.visitLineNumber(159, l16);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagList");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagList", "iterator",
					"()Ljava/util/Iterator;", false);
			mv.visitVarInsn(ASTORE, 4);
			Label l17 = new Label();
			mv.visitJumpInsn(GOTO, l17);
			Label l18 = new Label();
			mv.visitLabel(l18);
			mv.visitFrame(Opcodes.F_FULL, 5,
					new Object[] { "team/unstudio/udpl/core/nms/asm/NmsNBT", "team/unstudio/udpl/nms/nbt/NBTBase",
							"net/minecraft/server/" + nmsVersion + "/NBTTagList", Opcodes.TOP, "java/util/Iterator" },
					0, new Object[] {});
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTBase");
			mv.visitVarInsn(ASTORE, 3);
			Label l19 = new Label();
			mv.visitLabel(l19);
			mv.visitLineNumber(160, l19);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toNmsNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Lnet/minecraft/server/" + nmsVersion + "/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagList", "add",
					"(Lnet/minecraft/server/" + nmsVersion + "/NBTBase;)V", false);
			mv.visitLabel(l17);
			mv.visitLineNumber(159, l17);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l18);
			Label l20 = new Label();
			mv.visitLabel(l20);
			mv.visitLineNumber(161, l20);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l11);
			mv.visitLineNumber(163, l11);
			mv.visitFrame(Opcodes.F_FULL, 2,
					new Object[] { "team/unstudio/udpl/core/nms/asm/NmsNBT", "team/unstudio/udpl/nms/nbt/NBTBase" }, 0,
					new Object[] {});
			mv.visitVarInsn(ALOAD, 1);
			mv.visitTypeInsn(CHECKCAST, "team/unstudio/udpl/nms/nbt/NBTTagCompound");
			mv.visitVarInsn(ASTORE, 3);
			Label l21 = new Label();
			mv.visitLabel(l21);
			mv.visitLineNumber(164, l21);
			mv.visitTypeInsn(NEW, "net/minecraft/server/" + nmsVersion + "/NBTTagCompound");
			mv.visitInsn(DUP);
			mv.visitMethodInsn(INVOKESPECIAL, "net/minecraft/server/" + nmsVersion + "/NBTTagCompound", "<init>", "()V",
					false);
			mv.visitVarInsn(ASTORE, 4);
			Label l22 = new Label();
			mv.visitLabel(l22);
			mv.visitLineNumber(165, l22);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagCompound", "keySet",
					"()Ljava/util/Set;", false);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Set", "iterator", "()Ljava/util/Iterator;", true);
			mv.visitVarInsn(ASTORE, 6);
			Label l23 = new Label();
			mv.visitJumpInsn(GOTO, l23);
			Label l24 = new Label();
			mv.visitLabel(l24);
			mv.visitFrame(Opcodes.F_FULL, 7, new Object[] { "team/unstudio/udpl/core/nms/asm/NmsNBT",
					"team/unstudio/udpl/nms/nbt/NBTBase", Opcodes.TOP, "team/unstudio/udpl/nms/nbt/NBTTagCompound",
					"net/minecraft/server/" + nmsVersion + "/NBTTagCompound", Opcodes.TOP, "java/util/Iterator" }, 0,
					new Object[] {});
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "next", "()Ljava/lang/Object;", true);
			mv.visitTypeInsn(CHECKCAST, "java/lang/String");
			mv.visitVarInsn(ASTORE, 5);
			Label l25 = new Label();
			mv.visitLabel(l25);
			mv.visitLineNumber(166, l25);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTTagCompound", "get",
					"(Ljava/lang/String;)Lteam/unstudio/udpl/nms/nbt/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toNmsNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Lnet/minecraft/server/" + nmsVersion + "/NBTBase;", false);
			mv.visitMethodInsn(INVOKEVIRTUAL, "net/minecraft/server/" + nmsVersion + "/NBTTagCompound", "set",
					"(Ljava/lang/String;Lnet/minecraft/server/" + nmsVersion + "/NBTBase;)V", false);
			mv.visitLabel(l23);
			mv.visitLineNumber(165, l23);
			mv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
			mv.visitVarInsn(ALOAD, 6);
			mv.visitMethodInsn(INVOKEINTERFACE, "java/util/Iterator", "hasNext", "()Z", true);
			mv.visitJumpInsn(IFNE, l24);
			Label l26 = new Label();
			mv.visitLabel(l26);
			mv.visitLineNumber(167, l26);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l15);
			mv.visitLineNumber(169, l15);
			mv.visitFrame(Opcodes.F_FULL, 2,
					new Object[] { "team/unstudio/udpl/core/nms/asm/NmsNBT", "team/unstudio/udpl/nms/nbt/NBTBase" }, 0,
					new Object[] {});
			mv.visitInsn(ACONST_NULL);
			mv.visitInsn(ARETURN);
			Label l27 = new Label();
			mv.visitLabel(l27);
			mv.visitLocalVariable("this", "Lteam/unstudio/udpl/core/nms/asm/NmsNBT;", null, l0, l27, 0);
			mv.visitLocalVariable("nbt", "Lteam/unstudio/udpl/nms/nbt/NBTBase;", null, l0, l27, 1);
			mv.visitLocalVariable("nmsList", "Lnet/minecraft/server/" + nmsVersion + "/NBTTagList;", null, l16, l11, 2);
			mv.visitLocalVariable("nbtBase", "Lteam/unstudio/udpl/nms/nbt/NBTBase;", null, l19, l17, 3);
			mv.visitLocalVariable("oldNbtMap", "Lteam/unstudio/udpl/nms/nbt/NBTTagCompound;", null, l21, l15, 3);
			mv.visitLocalVariable("nmsMap", "Lnet/minecraft/server/" + nmsVersion + "/NBTTagCompound;", null, l22, l15,
					4);
			mv.visitLocalVariable("key", "Ljava/lang/String;", null, l25, l23, 5);
			mv.visitMaxs(5, 7);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC + ACC_BRIDGE + ACC_SYNTHETIC, "toNmsNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Ljava/lang/Object;", null, null);
			mv.visitCode();
			Label l0 = new Label();
			mv.visitLabel(l0);
			mv.visitLineNumber(1, l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/core/nms/asm/NmsNBT", "toNmsNBT",
					"(Lteam/unstudio/udpl/nms/nbt/NBTBase;)Lnet/minecraft/server/" + nmsVersion + "/NBTBase;", false);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_STATIC + ACC_SYNTHETIC, "$SWITCH_TABLE$team$unstudio$udpl$nms$nbt$NBTBaseType",
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/core/nms/asm/NmsNBT",
					"$SWITCH_TABLE$team$unstudio$udpl$nms$nbt$NBTBaseType", "[I");
			mv.visitInsn(DUP);
			Label l37 = new Label();
			mv.visitJumpInsn(IFNULL, l37);
			mv.visitInsn(ARETURN);
			mv.visitLabel(l37);
			mv.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[] { "[I" });
			mv.visitInsn(POP);
			mv.visitMethodInsn(INVOKESTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "values",
					"()[Lteam/unstudio/udpl/nms/nbt/NBTBaseType;", false);
			mv.visitInsn(ARRAYLENGTH);
			mv.visitIntInsn(NEWARRAY, T_INT);
			mv.visitVarInsn(ASTORE, 0);
			mv.visitLabel(l0);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "BYTE",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "BYTEARRAY",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "COMPOUND",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "DOUBLE",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "END",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "FLOAT",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "INTARRAY",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "INTEGER",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "LIST",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "LONG",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "SHORT",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(GETSTATIC, "team/unstudio/udpl/nms/nbt/NBTBaseType", "STRING",
					"Lteam/unstudio/udpl/nms/nbt/NBTBaseType;");
			mv.visitMethodInsn(INVOKEVIRTUAL, "team/unstudio/udpl/nms/nbt/NBTBaseType", "ordinal", "()I", false);
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
			mv.visitFieldInsn(PUTSTATIC, "team/unstudio/udpl/core/nms/asm/NmsNBT",
					"$SWITCH_TABLE$team$unstudio$udpl$nms$nbt$NBTBaseType", "[I");
			mv.visitInsn(ARETURN);
			mv.visitMaxs(3, 1);
			mv.visitEnd();
		}
		cw.visitEnd();

		return cw.toByteArray();
	}
}