package team.unstudio.udpl.command.anno;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.List;

import org.bukkit.command.CommandSender;

import team.unstudio.udpl.util.asm.*;
import static team.unstudio.udpl.util.asm.Opcodes.*;

public class TabCompleteWrapper {
	
	private final CommandNode node;
	private final AnnoCommandManager manager;
	
	private final String permission;
	private final Class<? extends CommandSender>[] senders;
	private final boolean allowOp;
	
	private final TabCompleteExecutor executor;
	
	public TabCompleteWrapper(CommandNode node, AnnoCommandManager manager, Object object, Method method, TabComplete tabComplete) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		this.node = requireNonNull(node);
		this.manager = requireNonNull(manager);
		
		requireNonNull(object);
		requireNonNull(method);
		
		requireNonNull(tabComplete);
		permission = tabComplete.permission();
		senders = tabComplete.senders();
		allowOp = tabComplete.allowOp();
		
		executor = loadExecutor(manager, object, method);
	}
	
	private TabCompleteExecutor loadExecutor(AnnoCommandManager manager, Object object, Method method) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		String className = getUniqueName(method);
		String objectType = Type.getInternalName(object.getClass());
		
		ClassWriter cw = new ClassWriter(0);
		FieldVisitor fv;
		MethodVisitor mv;

		cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, className, null,
				"java/lang/Object",
				new String[] { "team/unstudio/udpl/command/anno/TabCompleteWrapper$TabCompleteExecutor" });

		cw.visitSource(".dynamic", null);

		cw.visitInnerClass("team/unstudio/udpl/command/anno/TabCompleteWrapper$TabCompleteExecutor",
				"team/unstudio/udpl/command/anno/TabCompleteWrapper", "TabCompleteExecutor",
				ACC_PUBLIC + ACC_STATIC + ACC_ABSTRACT + ACC_INTERFACE);

		if (!isStatic) {
			fv = cw.visitField(ACC_PRIVATE + ACC_FINAL, "instance", "Ljava/lang/Object;", null, null);
			fv.visitEnd();
		}
		
		{
			mv = cw.visitMethod(ACC_PUBLIC, "<init>", isStatic ? "()V" : "(Ljava/lang/Object;)V", null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
			if (!isStatic) {
				mv.visitVarInsn(ALOAD, 0);
				mv.visitVarInsn(ALOAD, 1);
				mv.visitFieldInsn(PUTFIELD, className, "instance", "Ljava/lang/Object;");
			}
			mv.visitInsn(RETURN);
			mv.visitMaxs(2, 2);
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "invoke",
					"(Lorg/bukkit/command/CommandSender;[Ljava/lang/String;)Ljava/util/List;",
					"(Lorg/bukkit/command/CommandSender;[Ljava/lang/String;)Ljava/util/List<Ljava/lang/String;>;",
					null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			if(!isStatic) {
				mv.visitFieldInsn(GETFIELD, className, "instance", "Ljava/lang/Object;");
				mv.visitTypeInsn(CHECKCAST, objectType);
			}
			for(Parameter parameter : method.getParameters()) {
				if(CommandSender.class.isAssignableFrom(parameter.getType())) {
					mv.visitVarInsn(ALOAD, 1);
					mv.visitTypeInsn(CHECKCAST, Type.getInternalName(parameter.getType()));
				}else if(parameter.getType().equals(String[].class)) {
					mv.visitVarInsn(ALOAD, 2);
				}
			}
			mv.visitMethodInsn(isStatic ? INVOKESTATIC : INVOKEVIRTUAL, objectType,
					method.getName(), Type.getMethodDescriptor(method), false);
			mv.visitInsn(ARETURN);
			mv.visitMaxs(method.getParameterCount() + 1, 3);
			mv.visitEnd();
		}
		cw.visitEnd();

		Class<?> clazz = manager.getClassLoader().loadClass(cw.toByteArray());
		return (TabCompleteExecutor)(isStatic ? clazz.newInstance() : clazz.getDeclaredConstructor(Object.class).newInstance(object));
	}
	
	private static int id = 0;
	private String getUniqueName(Method method) {
		return String.format("TabComplete_%d_%s_%s", id++, method.getDeclaringClass().getSimpleName(), method.getName());
	}

	public CommandNode getNode() {
		return node;
	}

	public AnnoCommandManager getManager() {
		return manager;
	}
	
	public boolean checkPermission(CommandSender sender) {
		if(permission == null || permission.isEmpty()) 
			return true;
		
		if(allowOp && sender.isOp())
			return true;
		
		return sender.hasPermission(permission);
	}

	public boolean checkSender(CommandSender sender) {
		Class<?> senderClazz = sender.getClass();
		for (int i = 0; i < senders.length; i++)
			if (senders[i].isAssignableFrom(senderClazz))
				return true;
		
		return false;
	}
	
	public List<String> invoke(CommandSender sender, String[] args) {
		return executor.invoke(sender, args);
	}
	
	public static interface TabCompleteExecutor {
		
		List<String> invoke(CommandSender sender, String[] args);
	}

}
