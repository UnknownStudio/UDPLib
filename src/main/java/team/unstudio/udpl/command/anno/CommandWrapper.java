package team.unstudio.udpl.command.anno;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.command.CommandSender;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import team.unstudio.udpl.command.CommandResult;
import team.unstudio.udpl.core.UDPLib;

public class CommandWrapper {
	
	private final String node;
	private final AnnoCommandManager manager;
	private final Map<String,CommandWrapper> children = Maps.newHashMap();
	private final CommandWrapper parent;
	
	private Object obj;
	
	private Method command;
	private Method tabCompleter;
	
	private String permission;
	private Class<? extends CommandSender>[] senders;
	private String usage;
	private String description;
	private boolean allowOp;
	private boolean exactParameterMatching;
	
	private boolean hasStringArray;
	
	private Class<?>[] requireds;
	private Class<?>[] optionals;
	private List<List<String>> requiredCompletes;
	private String[] requiredUsages;
	private String[] optionalUsages;
	private List<List<String>> optionalCompletes;
	private Object[] optionalDefaults;

	public CommandWrapper(String node,AnnoCommandManager manager,CommandWrapper parent) {
		this.node = node.toLowerCase();
		this.manager = manager;
		this.parent = parent;
	}
	
	public String getNode() {
		return node;
	}

	public Map<String, CommandWrapper> getChildren() {
		return children;
	}
	
	public Object getObj() {
		return obj;
	}
	
	public String getPermission() {
		return permission;
	}

	public Class<? extends CommandSender>[] getSenders() {
		return senders;
	}

	public String getUsage() {
		return usage;
	}

	public String getDescription() {
		return description;
	}

	public boolean isAllowOp() {
		return allowOp;
	}
	
	public Method getMethod() {
		return command;
	}
	
	@Nullable
	public CommandWrapper getParent() {
		return parent;
	}
	
	public AnnoCommandManager getCommandManager() {
		return manager;
	}
	
	public String[] getRequiredUsages() {
		return requiredUsages;
	}

	public String[] getOptionalUsages() {
		return optionalUsages;
	}
	
	public CommandResult onCommand(CommandSender sender,org.bukkit.command.Command command,String label,String[] args) {
		if (obj == null)
			return CommandResult.UnknownCommand;
		
		if (!checkSender(sender))
			return CommandResult.WrongSender;

		if (!checkPermission(sender))
			return CommandResult.NoPermission;

		// 检查参数数量
		if (requireds.length > args.length)
			return CommandResult.NoEnoughParameter;
		
		// 精确参数匹配
		if (exactParameterMatching && requireds.length + optionals.length < args.length)
			return CommandResult.UnknownCommand;

		// 转换参数
		Object[] objs = new Object[this.command.getParameterTypes().length];
		objs[0] = sender;
		{
			List<Integer> errorParameterIndexsList = Lists.newArrayList();

			for (int i = 0, parameterLength = this.command.getParameterTypes().length
					- (hasStringArray ? 2 : 1); i < parameterLength; i++) {
				if (i < args.length)
					try{
						if (i < requireds.length)
							objs[i + 1] = transformParameter(requireds[i], args[i]);
						else
							objs[i + 1] = transformParameter(optionals[i - requireds.length], args[i]);
					}catch(Exception e){
						errorParameterIndexsList.add(i);
					}
				else
					objs[i + 1] = optionalDefaults[i - args.length];
			}

			int[] errorParameterIndexs = new int[errorParameterIndexsList.size()];
			for(int i=0,size = errorParameterIndexsList.size() ; i < size;i++)
				errorParameterIndexs[i] = errorParameterIndexsList.get(i);
			
			if (errorParameterIndexs.length!=0){
				getCommandManager().onErrorParameter(sender, command, label, args, this, errorParameterIndexs);
				return CommandResult.ErrorParameter;
			}
		}

		if (hasStringArray){
			if(requireds.length + optionals.length < args.length)
				objs[objs.length - 1] = Arrays.copyOfRange(args, requireds.length + optionals.length, args.length);
			else 
				objs[objs.length - 1] = new String[0];
		}
		

		// 执行指令
		try {
			if (this.command.getReturnType().equals(boolean.class))
				return (boolean) this.command.invoke(obj, objs) ? CommandResult.Success : CommandResult.Failure;
			else {
				this.command.invoke(obj, objs);
				return CommandResult.Success;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return CommandResult.Failure;
		}
	}
	
	private boolean checkSender(CommandSender sender){
		if(getSenders() == null)
			return false;
		
		for (Class<? extends CommandSender> s : getSenders())
			if (s.isAssignableFrom(sender.getClass()))
				return true;
		
		return false;
	}
	
	private boolean checkPermission(CommandSender sender){
		if (isAllowOp()&&sender.isOp())
			return true;
		
		if(getPermission() == null || getPermission().isEmpty())
			return true;
		
		if (sender.hasPermission(getPermission())) 
			return true;

		return false;
	}

	private Object transformParameter(Class<?> clazz,String value){
		return getCommandManager().transformParameter(clazz, value);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> onTabComplete(String[] args){
		if(tabCompleter==null){
			if(args.length<=requireds.length)
				return requiredCompletes.get(args.length-1);
			else if(args.length<=requireds.length+optionals.length)
				return optionalCompletes.get(args.length-requireds.length-1);
			else
				return Collections.EMPTY_LIST;
		}else{
			try {
				return (List<String>) tabCompleter.invoke(obj, new Object[]{args});
			} catch (Exception e) {
				return Collections.EMPTY_LIST;
			}
		}
	}
	
	public void setMethod(Object obj,Method method){
		this.obj = obj;
		this.command = method;
		method.setAccessible(true);
		Command anno = method.getAnnotation(Command.class);
		permission = anno.permission();
		senders = anno.senders();
		usage = anno.usage();
		description = anno.description();
		allowOp = anno.allowOp();
		exactParameterMatching = anno.exactParameterMatching();
		
		Class<?>[] parameterTypes = method.getParameterTypes();
		hasStringArray = parameterTypes[parameterTypes.length-1].equals(String[].class);
		
		//参数载入
		List<Class<?>> requireds = new ArrayList<>();
		List<Class<?>> optionals = new ArrayList<>();
		List<String> requiredUsages = new ArrayList<>();
		List<String> optionalUsages = new ArrayList<>();
		requiredCompletes = new ArrayList<>();
		optionalCompletes = new ArrayList<>();
		List<Object> optionalDefaults = new ArrayList<>();
		
		Parameter[] parameters = method.getParameters();
		for(int i=0;i<parameters.length;i++){
			{
				Required annoRequired = parameters[i].getAnnotation(Required.class);
				if(annoRequired!=null){
					requireds.add(parameters[i].getType());
					requiredUsages.add(annoRequired.usage() == null || annoRequired.usage().isEmpty()
							? parameters[i].getName() : annoRequired.usage());
					requiredCompletes.add(Collections.unmodifiableList(Arrays.asList(annoRequired.complete())));
					continue;
				}
			}
			
			{
				Optional annoOptional = parameters[i].getAnnotation(Optional.class);
				if(annoOptional!=null){
					optionals.add(parameters[i].getType());
					optionalUsages.add(annoOptional.usage() == null || annoOptional.usage().isEmpty()
							? parameters[i].getName() : annoOptional.usage());
					optionalDefaults.add(transformParameter(parameters[i].getType(), annoOptional.value()));
					optionalCompletes.add(Collections.unmodifiableList(Arrays.asList(annoOptional.complete())));
					continue;
				}
			}
		}
		this.requireds = requireds.toArray(new Class<?>[requireds.size()]);
		this.optionals = optionals.toArray(new Class<?>[optionals.size()]);
		this.requiredUsages = requiredUsages.toArray(new String[requiredUsages.size()]);
		this.optionalUsages = optionalUsages.toArray(new String[optionalUsages.size()]);
		this.optionalDefaults = optionalDefaults.toArray(new Object[optionalDefaults.size()]);
		
		//自动补全载入
		for(Method m:obj.getClass().getDeclaredMethods()){
			TabComplete tab = m.getAnnotation(TabComplete.class);
			if(tab == null)
				continue;
			
			if(Arrays.equals(tab.value(), anno.value())){
				m.setAccessible(true);
				tabCompleter = m;
				break;
			}
		}
	}
}
