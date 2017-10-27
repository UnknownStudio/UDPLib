package team.unstudio.udpl;

import org.apache.commons.lang.StringUtils;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import team.unstudio.udpl.core.nms.asm.DynamicClassLoader;
import team.unstudio.udpl.util.asm.ClassWriter;
import team.unstudio.udpl.util.asm.MethodVisitor;
import team.unstudio.udpl.util.asm.Opcodes;

import java.io.FileOutputStream;

import static org.junit.Assert.assertEquals;

public class ASMTest implements Opcodes {
//    // access flags 0x9
//    public static test([Ljava/lang/String;)Ljava/lang/String;
//    L0
//    LINENUMBER 17 L0
//    ALOAD 0
//    INVOKESTATIC org/apache/commons/lang/StringUtils.join ([Ljava/lang/Object;)Ljava/lang/String;
//    ARETURN
//            L1
//    LOCALVARIABLE strings [Ljava/lang/String; L0 L1 0
//    MAXSTACK = 1
//    MAXLOCALS = 1

    public static String test(String[] strings){
        return StringUtils.join(strings);
    }

    @Test
    public void createClass() throws Exception {
        ClassWriter cw = new ClassWriter(0);
        cw.visit(V1_8, ACC_PUBLIC, "ASMClass", null, "java/lang/Object", null);

        //生成默认的构造方法
        MethodVisitor mw = cw.visitMethod(ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null);

        //生成构造方法的字节码指令
        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();

        mw = cw.visitMethod(ACC_PUBLIC + ACC_STATIC,
                "test",
                "([Ljava/lang/String;)Ljava/lang/String;",
                null,
                null);

        mw.visitVarInsn(ALOAD, 0);
        mw.visitMethodInsn(INVOKESTATIC, "org/apache/commons/lang/StringUtils", "join", "([Ljava/lang/Object;)Ljava/lang/String;", false);
        mw.visitInsn(ARETURN);

        mw.visitMaxs(1, 1);

        //字节码生成完成
        mw.visitEnd();

        // 获取生成的class文件对应的二进制流
        byte[] code = cw.toByteArray();

        Class<?> exampleClass = new DynamicClassLoader().loadClass("ASMClass", code, 0, code.length);

        //通过反射调用main方法
        String output = (String) exampleClass.getMethods()[0].invoke(null, new Object[] { new String[]{"Hello", " ", "ASM", "!"} });
        assertEquals("Hello ASM!" ,output);
    }
}
