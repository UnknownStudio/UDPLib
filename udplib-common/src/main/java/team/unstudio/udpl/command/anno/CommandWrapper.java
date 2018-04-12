package team.unstudio.udpl.command.anno;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.command.CommandSender;
import com.google.common.base.Strings;

import team.unstudio.udpl.util.asm.*;
import static team.unstudio.udpl.util.asm.Opcodes.*;

public class CommandWrapper {
	private final CommandNode node;
	private final AnnoCommandManager manager;
	
	private final Object object;
	private final Method method;
	
	private final String permission;
	private final Class<? extends CommandSender>[] senders;
	private final boolean allowOp;
	private final boolean exactParameterMatching;
	
	private final String usage;
	private final String description;
	
	private final boolean hasStringArray;
	
	private final RequiredWrapper[] requireds;
	private final OptionalWrapper[] optionals;
	
	private final CommandExecutor executor;
	
	public CommandWrapper(CommandNode node, AnnoCommandManager manager, Object object, Method method, team.unstudio.udpl.command.anno.Command command) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		this.node = requireNonNull(node);
		this.manager = requireNonNull(manager);
		
		this.object = requireNonNull(object);
		this.method = requireNonNull(method);
		
		requireNonNull(command);
		permission = command.permission();
		senders = command.senders();
		allowOp = command.allowOp();
		exactParameterMatching = command.exactParameterMatching();
		
		usage = Strings.nullToEmpty(command.usage());
		description = Strings.nullToEmpty(command.description());
		
		List<RequiredWrapper> requireds = new ArrayList<>();
		List<OptionalWrapper> optionals = new ArrayList<>();
		for(Parameter parameter : method.getParameters()) {
			Required required = parameter.getAnnotation(Required.class);
			if(required != null) {
				requireds.add(new RequiredWrapper(parameter.getType(), required));
				continue;
			}
			Optional optional = parameter.getAnnotation(Optional.class);
			if(optional != null) {
				optionals.add(new OptionalWrapper(parameter.getType(), optional));
			}
		}
		this.requireds = requireds.toArray(new RequiredWrapper[requireds.size()]);
		this.optionals = optionals.toArray(new OptionalWrapper[optionals.size()]);
		
		this.hasStringArray = method.getParameterTypes()[method.getParameterCount()-1].equals(String[].class);
		
		this.executor = loadExecutor(manager, object, method);
	}
	
	private CommandExecutor loadExecutor(AnnoCommandManager manager, Object object, Method method) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		
		boolean isStatic = Modifier.isStatic(method.getModifiers());
		String className = getUniqueName(method);
		String objectType = Type.getInternalName(object.getClass());
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
		FieldVisitor fv;
		MethodVisitor mv;

		cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, className, null,
				"java/lang/Object",
				new String[] { "team/unstudio/udpl/command/anno/CommandWrapper$CommandExecutor" });

		cw.visitSource(".dynamic", null);

		cw.visitInnerClass("team/unstudio/udpl/command/anno/CommandWrapper$CommandExecutor",
				"team/unstudio/udpl/command/anno/CommandWrapper", "CommandExecutor",
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
			mv.visitEnd();
		}
		{
			mv = cw.visitMethod(ACC_PUBLIC, "invoke",
					"(Lorg/bukkit/command/CommandSender;[Ljava/lang/Object;[Ljava/lang/Object;[Ljava/lang/String;)V",
					null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			if(!isStatic) {
				mv.visitFieldInsn(GETFIELD, className, "instance", "Ljava/lang/Object;");
				mv.visitTypeInsn(CHECKCAST, objectType);
			}
			
			int requiredCount = 0, optionalCount = 0;
			for(Parameter parameter : method.getParameters()) {
				if(parameter.getAnnotation(Required.class) != null) {
					visitArray(mv, 2, requiredCount++, parameter.getType());
				}else if(parameter.getAnnotation(Optional.class) != null) {
					visitArray(mv, 3, optionalCount++, parameter.getType());
				}else if(CommandSender.class.isAssignableFrom(parameter.getType())) {
					mv.visitVarInsn(ALOAD, 1);
					mv.visitTypeInsn(CHECKCAST, Type.getInternalName(parameter.getType()));
				}else if(parameter.getType().equals(String[].class)) {
					mv.visitVarInsn(ALOAD, 4);
				}
			}
			
			mv.visitMethodInsn(isStatic ? INVOKESTATIC : INVOKEVIRTUAL, objectType,
					method.getName(), Type.getMethodDescriptor(method), false);
			mv.visitInsn(RETURN);
			mv.visitEnd();
		}
		cw.visitEnd();

		Class<?> clazz = manager.getClassLoader().loadClass(cw.toByteArray());
		return (CommandExecutor)(isStatic ? clazz.newInstance() : clazz.getDeclaredConstructor(Object.class).newInstance(object));
	}
	
	private static int id = 0;
	private String getUniqueName(Method method) {
		return String.format("Command_%d_%s_%s", id++, method.getDeclaringClass().getSimpleName(), method.getName());
	}
	
	private void visitArray(MethodVisitor mv, int varIndex, int arrayIndex, Class<?> targetClass) {
		String targetType = Type.getInternalName(targetClass);
		mv.visitVarInsn(ALOAD, varIndex);
        if (arrayIndex >= -1 && arrayIndex <= 5) {
            mv.visitInsn(Opcodes.ICONST_0 + arrayIndex);
        } else if (arrayIndex >= Byte.MIN_VALUE && arrayIndex <= Byte.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.BIPUSH, arrayIndex);
        } else if (arrayIndex >= Short.MIN_VALUE && arrayIndex <= Short.MAX_VALUE) {
            mv.visitIntInsn(Opcodes.SIPUSH, arrayIndex);
        } else {
            mv.visitLdcInsn(arrayIndex);
        }
		mv.visitInsn(AALOAD);
		if(targetClass.isPrimitive()) {
			if (targetClass == int.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I", false);
			} else if (targetClass == double.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D", false);
			} else if (targetClass == float.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Float");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F", false);
			} else if (targetClass == long.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J", false);
			} else if (targetClass == short.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S", false);
			} else if (targetClass == byte.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B", false);
			} else if (targetClass == boolean.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z", false);
			} else if (targetClass == char.class) {
				mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
				mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C", false);
			}
		} else {
			mv.visitTypeInsn(CHECKCAST, targetType);
		}
	}

	public CommandNode getNode() {
		return node;
	}

	public AnnoCommandManager getManager() {
		return manager;
	}
	
	public Object getObject() {
		return object;
	}

	public Method getMethod() {
		return method;
	}
	
	public String getUsage() {
		return usage;
	}

	public String getDescription() {
		return description;
	}
	
	public String getPermission() {
		return permission;
	}

	public boolean isExactParameterMatching() {
		return exactParameterMatching;
	}
	
	public boolean hasStringArray() {
		return hasStringArray;
	}
	
	public RequiredWrapper[] getRequireds() {
		return requireds;
	}
	
	public OptionalWrapper[] getOptionals() {
		return optionals;
	}

	public boolean checkPermission(CommandSender sender) {
		if(permission == null || permission.isEmpty()) 
			return true;
		
		if(allowOp && sender.isOp())
			return true;
		
		return sender.hasPermission(permission);
	}

	public boolean checkSender(CommandSender sender) {
		return checkSender(sender.getClass());
	}
	
	public boolean checkSender(Class<? extends CommandSender> sender) {
		for (int i = 0; i < senders.length; i++)
			if (senders[i].isAssignableFrom(sender))
				return true;
		
		return false;
	}
	
	public void invoke(CommandSender sender, Object[] requireds, Object[] optionals, String[] args) {
		executor.invoke(sender, requireds, optionals, args);
	}

	public static class RequiredWrapper {
		
		private final Class<?> type;
		private final String name;
		private final String usage;
		private final String[] complete;
				
		public RequiredWrapper(Class<?> type, Required required) {
			this.type = requireNonNull(type);
			requireNonNull(required);
			this.name = Strings.nullToEmpty(required.name());
			this.usage = Strings.nullToEmpty(required.usage());
			this.complete = required.complete() != null ? required.complete() : new String[0];
		}

		public Class<?> getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getUsage() {
			return usage;
		}

		public String[] getComplete() {
			return complete;
		}
	}
	
	public static class OptionalWrapper {
		
		private final Class<?> type;
		private final String name;
		private final String usage;
		private final String[] complete;
		private final String defaultValue;
				
		public OptionalWrapper(Class<?> type, Optional optional) {
			this.type = requireNonNull(type);
			requireNonNull(optional);
			this.name = Strings.nullToEmpty(optional.name());
			this.usage = Strings.nullToEmpty(optional.usage());
			this.complete = optional.complete() != null ? optional.complete() : new String[0];
			this.defaultValue = Strings.nullToEmpty(optional.value());
		}

		public Class<?> getType() {
			return type;
		}

		public String getName() {
			return name;
		}

		public String getUsage() {
			return usage;
		}

		public String[] getComplete() {
			return complete;
		}

		public String getDefaultValue() {
			return defaultValue;
		}
	}

	public static interface CommandExecutor {
		
		void invoke(CommandSender sender, Object[] requireds, Object[] optionals, String[] args);
	}
}
