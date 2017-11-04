package team.unstudio.udpl.command.anno;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandSender;
import team.unstudio.udpl.command.CommandResult;

import javax.annotation.Nullable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandWrapper {
	
	private final String node;
	private final AnnoCommandManager manager;
	private final Map<String,CommandWrapper> children = Maps.newHashMap();
	private final CommandWrapper parent;
	
	private Object commandObject;
	private Method command;
	
	private Object tabCompleterObject;
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
	private String[] requiredNames;
	private String[] optionalNames;
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
	
	@Nullable
	public CommandWrapper getParent() {
		return parent;
	}
	
	public AnnoCommandManager getCommandManager() {
		return manager;
	}
	
	public String[] getRequiredNames() {
		return requiredNames;
	}

	public String[] getOptionalNames() {
		return optionalNames;
	}
	
	public boolean hasCommand(){
		return command != null;
	}
	
	public boolean hasTabComplete(){
		return tabCompleter != null;
	}
	
	public CommandResult onCommand(CommandSender sender,org.bukkit.command.Command command,String label,String[] args) {
		if (!hasCommand())
			return CommandResult.UNKNOWN_COMMAND;
		
		if (!checkSender(sender))
			return CommandResult.WRONG_SENDER;

		if (!checkPermission(sender))
			return CommandResult.NO_PERMISSION;

		// 检查参数数量
		if (requireds.length > args.length)
			return CommandResult.NO_ENOUGH_PARAMETER;
		
		// 精确参数匹配
		if (exactParameterMatching && requireds.length + optionals.length < args.length)
			return CommandResult.UNKNOWN_COMMAND;

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
				return CommandResult.ERROR_PARAMETER;
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
				return (boolean) this.command.invoke(commandObject, objs) ? CommandResult.SUCCESS : CommandResult.FAILURE;
			else {
				this.command.invoke(commandObject, objs);
				return CommandResult.SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return CommandResult.FAILURE;
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
		List<String> tabComplete = Lists.newArrayList();
		int index = args.length-1;
		
		if(index >= 0){
			String prefix = args[index];
			
			//Parameter
			if (args.length <= requireds.length){
				requiredCompletes.get(index).stream().filter(value->value.startsWith(prefix)).forEach(tabComplete::add);
				tabComplete.addAll(manager.tabCompleteParameter(requireds[index], prefix));
			}else if (args.length <= requireds.length + optionals.length){
				optionalCompletes.get(index - requireds.length).stream().filter(value->value.startsWith(prefix)).forEach(tabComplete::add);
				tabComplete.addAll(manager.tabCompleteParameter(optionals[index - requireds.length], prefix));
			}
			
			//Sub Command
			getChildren().keySet().stream().filter(node->node.startsWith(prefix)).forEach(tabComplete::add);
		}
		
		if(hasTabComplete()){
			try {
				List<String> completed = (List<String>) tabCompleter.invoke(tabCompleterObject, new Object[]{args});
				if(completed != null)
					tabComplete.addAll(completed);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return tabComplete;
	}
	
	public void setCommandMethod(Object obj,Command anno,Method method){
		Validate.notNull(obj);
		Validate.notNull(anno);
		Validate.notNull(method);
		this.commandObject = obj;
		this.command = method;
		this.command.setAccessible(true);
		this.permission = anno.permission();
		this.senders = anno.senders();
		this.usage = anno.usage();
		this.description = anno.description();
		this.allowOp = anno.allowOp();
		this.exactParameterMatching = anno.exactParameterMatching();
		
		Class<?>[] parameterTypes = method.getParameterTypes();
		this.hasStringArray = parameterTypes[parameterTypes.length-1].equals(String[].class);
		
		//参数载入
		List<Class<?>> requireds = new ArrayList<>();
		List<Class<?>> optionals = new ArrayList<>();
		List<String> requiredNames = new ArrayList<>();
		List<String> optionalNames = new ArrayList<>();
		List<List<String>> requiredCompletes = new ArrayList<>();
		List<List<String>> optionalCompletes = new ArrayList<>();
		List<Object> optionalDefaults = new ArrayList<>();
		
		Parameter[] parameters = method.getParameters();
		for(int i=0;i<parameters.length;i++){
			{
				Required annoRequired = parameters[i].getAnnotation(Required.class);
				if(annoRequired!=null){
					requireds.add(parameters[i].getType());
					requiredNames.add(annoRequired.name() == null || annoRequired.name().isEmpty()
							? parameters[i].getName() : annoRequired.name());
					requiredCompletes.add(ImmutableList.copyOf(annoRequired.complete()));
					continue;
				}
			}
			
			{
				Optional annoOptional = parameters[i].getAnnotation(Optional.class);
				if(annoOptional!=null){
					optionals.add(parameters[i].getType());
					optionalNames.add(annoOptional.name() == null || annoOptional.name().isEmpty()
							? parameters[i].getName() : annoOptional.name());
					optionalDefaults.add(transformParameter(parameters[i].getType(), annoOptional.value()));
					optionalCompletes.add(ImmutableList.copyOf(annoOptional.complete()));
					continue;
				}
			}
		}
		
		this.requireds = requireds.toArray(new Class<?>[requireds.size()]);
		this.optionals = optionals.toArray(new Class<?>[optionals.size()]);
		this.requiredNames = requiredNames.toArray(new String[requiredNames.size()]);
		this.requiredCompletes = ImmutableList.copyOf(requiredCompletes);
		this.optionalNames = optionalNames.toArray(new String[optionalNames.size()]);
		this.optionalDefaults = optionalDefaults.toArray(new Object[optionalDefaults.size()]);
		this.optionalCompletes = ImmutableList.copyOf(optionalCompletes);
	}
	
	public void setTabCompleteMethod(Object object, TabComplete anno, Method tabComplete){
		Validate.notNull(object);
		Validate.notNull(anno);
		Validate.notNull(tabComplete);
		this.tabCompleterObject = object;
		this.tabCompleter = tabComplete;
	}
}
