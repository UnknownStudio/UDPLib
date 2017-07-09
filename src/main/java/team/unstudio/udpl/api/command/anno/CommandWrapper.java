package team.unstudio.udpl.api.command.anno;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unstudio.udpl.api.command.CommandResult;

public class CommandWrapper {
	
	private final String node;
	private final List<CommandWrapper> children = new ArrayList<>();
	
	private Object obj;
	private Command anno;
	private Method method;
	private Method tabcomlete;

	public CommandWrapper(String node) {
		this.node = node;
	}
	
	public CommandResult onCommand(CommandSender sender,String[] args){
		boolean flag = true;
		for (Class<? extends CommandSender> s : anno.senders()) {
			if (s.isAssignableFrom(sender.getClass())) {
				flag = false;
				break;
			}
		}
		if (flag) return CommandResult.WrongSender;

		if (anno.permission() != null && !anno.permission().isEmpty() && !sender.hasPermission(anno.permission())) return CommandResult.NoPermission;

		if (anno.parameterTypes().length != args.length) return CommandResult.NoEnoughParameter;

		Object[] objs = new Object[method.getParameterTypes().length];
		objs[0] = sender;
		for (int i = 1; i < args.length; i++) {
			try {
				String s = args[i];
				Class<?> clazz = anno.parameterTypes()[i];
				if (clazz.equals(String.class))
					objs[i] = s;
				else if (clazz.equals(int.class) || clazz.equals(Integer.class))
					objs[i] = Integer.parseInt(s);
				else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
					objs[i] = Boolean.parseBoolean(s);
				else if (clazz.equals(float.class) || clazz.equals(Float.class))
					objs[i] = Float.parseFloat(s);
				else if (clazz.equals(double.class) || clazz.equals(Double.class))
					objs[i] = Double.parseDouble(s);
				else if (clazz.equals(long.class) || clazz.equals(Long.class))
					objs[i] = Long.parseLong(s);
				else if (clazz.equals(byte.class) || clazz.equals(Byte.class))
					objs[i] = Byte.parseByte(s);
				else if (clazz.equals(short.class) || clazz.equals(Short.class))
					objs[i] = Short.parseShort(s);
				else
					objs[i] = s;
			} catch (Exception e) {
				return CommandResult.ErrorParameter;
			}
		}
		
		try {
			if(method.getReturnType().equals(boolean.class))
				return (boolean)method.invoke(obj, objs)?CommandResult.Success:CommandResult.Failure;
			else {
				method.invoke(obj, objs);
				return CommandResult.Success;
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			return CommandResult.Failure;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<String> onTabComplete(String[] args){
		if(method==null){
			List<String> list = new ArrayList<>();
			for(Player player:Bukkit.getOnlinePlayers())if(player.getName().startsWith(args[0]))list.add(player.getName());
			return list;
		}else{
			try {
				return (List<String>) tabcomlete.invoke(obj, args);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				return new ArrayList<>();
			}
		}
	}

	public String getNode() {
		return node;
	}

	public List<CommandWrapper> getChildren() {
		return children;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public Command getAnno() {
		return anno;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Object obj,Method method){
		this.obj = obj;
		this.method = method;
		method.setAccessible(true);
		anno = method.getAnnotation(Command.class);
		for(Method m:obj.getClass().getDeclaredMethods()){
			TabComplete tab;
			if((tab=m.getAnnotation(TabComplete.class))==null)continue;
			
			if(tab.value().equals(anno.value())){
				m.setAccessible(true);
				tabcomlete = m;
				break;
			}
		}
	}
}
