package team.unstudio.udpl.command.anno;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import team.unstudio.udpl.command.CommandResult;

public class CommandWrapper {
	
	private final String node;
	private final List<CommandWrapper> children = new ArrayList<>();
	
	private Object obj;
	private Command anno;
	private Method method;
	private Method tabcomplete;
	private Class<?>[] requireds;
	private Class<?>[] optionals;
	private String[] optionalDefaults;

	public CommandWrapper(String node) {
		this.node = node;
	}
	
	public CommandResult onCommand(CommandSender sender,String[] args){
		//检查Sender
		if(anno.senders() != null && anno.senders().length != 0){
			boolean flag = true;
			for (Class<? extends CommandSender> s : anno.senders()) {
				if (s.isAssignableFrom(sender.getClass())) {
					flag = false;
					break;
				}
			}
			if (flag) return CommandResult.WrongSender;
		}

		//检查权限
		if (!(anno.allowOp()&&sender.isOp()) &&
				anno.permissions() != null && anno.permissions().length != 0) {
			boolean flag = true;
			for (String permission : anno.permissions()) {
				if (sender.hasPermission(permission)) {
					flag = false;
					break;
				}
			}

			if (flag)
				return CommandResult.NoPermission;
		}
			

		//检查参数数量
		if (requireds.length > args.length) 
			return CommandResult.NoEnoughParameter;

		//转换参数
		Object[] objs = new Object[method.getParameterTypes().length];
		objs[0] = sender;
		
		try {
			for (int i = 0,parameterLength = method.getParameterTypes().length-1; i < parameterLength; i++) {
				if(i<args.length)
					if(i<requireds.length)
						objs[i+1] = transformParameter(requireds[i], args[i]);
					else
						objs[i+1] = transformParameter(optionals[i-requireds.length], args[i]);
				else
					objs[i+1] = transformParameter(optionals[i-args.length], optionalDefaults[i-args.length]);
			}
		} catch (Exception e) {
			return CommandResult.ErrorParameter;
		}
		
		//执行指令
		try {
			if(method.getReturnType().equals(boolean.class))
				return (boolean)method.invoke(obj, objs)?CommandResult.Success:CommandResult.Failure;
			else {
				method.invoke(obj, objs);
				return CommandResult.Success;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return CommandResult.Failure;
		}
	}
	
	protected Object transformParameter(Class<?> clazz,String value){
		if(value.isEmpty())
			return null;
		else if (clazz.equals(String.class))
			return value;
		else if (clazz.equals(int.class) || clazz.equals(Integer.class))
			return Integer.parseInt(value);
		else if (clazz.equals(boolean.class) || clazz.equals(Boolean.class))
			return Boolean.parseBoolean(value);
		else if (clazz.equals(float.class) || clazz.equals(Float.class))
			return Float.parseFloat(value);
		else if (clazz.equals(double.class) || clazz.equals(Double.class))
			return Double.parseDouble(value);
		else if (clazz.equals(long.class) || clazz.equals(Long.class))
			return Long.parseLong(value);
		else if (clazz.equals(byte.class) || clazz.equals(Byte.class))
			return Byte.parseByte(value);
		else if (clazz.equals(short.class) || clazz.equals(Short.class))
			return Short.parseShort(value);
		else if (clazz.equals(Player.class))
			return Bukkit.getPlayer(value);
		else if (clazz.equals(OfflinePlayer.class))
			return Bukkit.getOfflinePlayer(value);
		else if (clazz.equals(Material.class))
			return Material.valueOf(value);
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> onTabComplete(String[] args){
		if(method==null){
			List<String> list = new ArrayList<>();
			for(Player player:Bukkit.getOnlinePlayers())
				if(player.getName().startsWith(args[0]))
					list.add(player.getName());
			return list;
		}else{
			try {
				return (List<String>) tabcomplete.invoke(obj, args);
			} catch (Exception e) {
				return Collections.EMPTY_LIST;
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
		
		//参数载入
		List<Class<?>> requireds = new ArrayList<>();
		List<Class<?>> optionals = new ArrayList<>();
		List<String> optionalDefaults = new ArrayList<>();
		Parameter[] parameters = method.getParameters();
		for(int i=0;i<parameters.length;i++){
			Required annoRequired = parameters[i].getAnnotation(Required.class);
			if(annoRequired!=null){
				requireds.add(parameters[i].getType());
				continue;
			}
			
			Optional annoOptional = parameters[i].getAnnotation(Optional.class);
			if(annoOptional!=null){
				optionals.add(parameters[i].getType());
				optionalDefaults.add(annoOptional.value());
				continue;
			}
		}
		this.requireds = requireds.toArray(new Class<?>[requireds.size()]);
		this.optionals = optionals.toArray(new Class<?>[optionals.size()]);
		this.optionalDefaults = optionalDefaults.toArray(new String[optionalDefaults.size()]);
		
		//自动补全载入
		for(Method m:obj.getClass().getDeclaredMethods()){
			TabComplete tab;
			if((tab=m.getAnnotation(TabComplete.class))==null)continue;
			
			if(tab.value().equals(anno.value())){
				m.setAccessible(true);
				tabcomplete = m;
				break;
			}
		}
	}
}
